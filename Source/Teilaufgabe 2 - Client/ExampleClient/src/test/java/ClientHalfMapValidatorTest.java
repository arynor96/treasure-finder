import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import halfmap.ClientHalfMapGenerator;
import halfmap.HalfMapFortPlacer;
import halfmap.HalfMapValidator;
import map.ClientMapNode;
import map.Coordinate;
import map.EFort;
import map.ETerrainClient;

// 3 testarten: positiv, negativ, datengetr. test
// positiv test -> erwartete/typisches verhalten
// negativ test -> absichtlich ein fehler, sollte gemeldet sein
// datengetr. test -> positiv test, verschieden daten für Test.

public class ClientHalfMapValidatorTest {

	private Map<Coordinate, ClientMapNode> nodes = new HashMap<Coordinate, ClientMapNode>();

	@BeforeAll
	public static void setUpBeforeTest() {

	}

	@AfterAll
	public static void tearDownAfterTest() {

	}

	@BeforeEach
	public void setUp() {

		for (int x = 0; x <= ClientHalfMapGenerator.HALF_MAP_LENGTH; x++) {
			for (int y = 0; y <= ClientHalfMapGenerator.HALF_MAP_HEIGHT; y++) {

				ClientMapNode node = new ClientMapNode(ETerrainClient.GRASS);
				nodes.put(new Coordinate(x, y), node);

				if (y == 2) {
					node = new ClientMapNode(ETerrainClient.MOUNTAIN);
					nodes.put(new Coordinate(x, y), node);
				}

				if (y == 4 && x > 2) {
					node = new ClientMapNode(ETerrainClient.MOUNTAIN);
					nodes.put(new Coordinate(x, y), node);
				}

				if (y == 3 && x >= 1 && x < ClientHalfMapGenerator.HALF_MAP_LENGTH) {
					node = new ClientMapNode(ETerrainClient.WATER);
					nodes.put(new Coordinate(x, y), node);
				}
			}
		}

	}

	@AfterEach
	public void tearUp() {
	}

	// Positive test.
	@Test
	public void GivenOneManualGeneratedHalfMap_VerifyingMapRules_ExpectValidMap() {

		// Arrange

		nodes.get(new Coordinate(6, 1)).setFortState(EFort.MYFORT);

		// Act
		HalfMapValidator validator = new HalfMapValidator(nodes);
		boolean mapValid = validator.validateMap();

		// Assert
		assertThat(mapValid, is(equalTo(true)));

	}

	// Positive test.
	@Test
	public void GivenOneManualGeneratedHalfMap_PlaceFort_ExpectValidPosition() {

		// Arrange
		boolean fortPositionValid = false;

		// Act
		Coordinate fort = HalfMapFortPlacer.placeFort(nodes);
		nodes.get(fort).setFortState(EFort.MYFORT);
		for (Map.Entry<Coordinate, ClientMapNode> entry : nodes.entrySet()) {
			ClientMapNode currentNode = entry.getValue();
			if (currentNode.getFortState() == EFort.MYFORT && currentNode.getTerrainType() == ETerrainClient.GRASS) {
				fortPositionValid = true;
			}
		}

		// Assert
		assertThat(fortPositionValid, is(equalTo(true)));

	}

	// Positive test.
	@ParameterizedTest
	@MethodSource("provideDataForMapValidator")
	public void GivenOneRandomGeneratedHalfMap_VerifyingAllMapRules_ExpectValidMap(
			Map<Coordinate, ClientMapNode> nodes) {

		HalfMapValidator validator = new HalfMapValidator(nodes);
		boolean mapValid = validator.validateMap();

		assertThat(mapValid, is(equalTo(true)));

	}

	@Test
	public void GivenOneManualGeneratedHalfMap_VerifyTooMuchWaterOnEdges_ExpectedInvalidMap() {

		for (int y = 0; y <= ClientHalfMapGenerator.HALF_MAP_HEIGHT; y++) {
			nodes.get(new Coordinate(0, y)).setTerrainType(ETerrainClient.WATER);
		}

		HalfMapValidator validator = new HalfMapValidator(nodes);
		boolean mapValid = validator.validateMap();

		assertThat(mapValid, is(equalTo(false)));

	}

	@Test
	public void GivenOneManualGeneratedHalfMap_VerifyIfIslandExists_ExpectedInvalidMap() {

		nodes.get(new Coordinate(3, 2)).setTerrainType(ETerrainClient.WATER);
		nodes.get(new Coordinate(5, 2)).setTerrainType(ETerrainClient.WATER);
		nodes.get(new Coordinate(4, 1)).setTerrainType(ETerrainClient.WATER);

		HalfMapValidator validator = new HalfMapValidator(nodes);
		boolean mapValid = validator.validateMap();

		assertThat(mapValid, is(equalTo(false)));

	}

	private static Stream<Arguments> provideDataForMapValidator() {
		return Stream.of(Arguments.of(ClientHalfMapGenerator.generateHalfMap().getNodes()),
				Arguments.of(ClientHalfMapGenerator.generateHalfMap().getNodes()),
				Arguments.of(ClientHalfMapGenerator.generateHalfMap().getNodes()),
				Arguments.of(ClientHalfMapGenerator.generateHalfMap().getNodes()));
	}

}
