import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.util.Map;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import halfmap.ClientHalfMapGenerator;
import map.ClientFullMap;
import map.ClientMapNode;
import map.Coordinate;
import map.ETerrainClient;
import movement.targetfinder.ClosestUnexploredNodeFinder;

public class ClosestUnexploredNodeFinderTest {

	private ClientFullMap halfMap1 = new ClientFullMap();
	private ClientFullMap halfMap2 = new ClientFullMap();
	private ClientFullMap fullMap = new ClientFullMap();

	@BeforeAll
	public static void setUpBeforeTest() {

	}

	@AfterAll
	public static void tearDownAfterTest() {

	}

	@BeforeEach
	public void setUp() {

		halfMap1 = ClientHalfMapGenerator.generateHalfMap();
		halfMap2 = ClientHalfMapGenerator.generateHalfMap();
		fullMap = FullMapBuilder.mergeMapsTreasureVisible(halfMap1, halfMap2);

	}

	@AfterEach
	public void tearUp() {

		halfMap1 = new ClientFullMap();
		halfMap2 = new ClientFullMap();
		fullMap = new ClientFullMap();
	}

	@Test
	public void GivenOneMapWithOnlyOneUnvisitedField_FindThatGrassField_ExpectedThatGrassField() {

		// Arrange

		for (Map.Entry<Coordinate, ClientMapNode> entry : fullMap.getNodes().entrySet()) {
			entry.getValue().setVisited(true);
		}
		Coordinate target = new Coordinate(3, 3);
		fullMap.getNodes().get(target).setVisited(false);
		fullMap.getNodes().get(target).setTerrainType(ETerrainClient.GRASS);

		Coordinate myPos = new Coordinate(9, 4);

		// Act
		Coordinate shouldBeTarget = ClosestUnexploredNodeFinder.calculateClosestUnexploredNode(fullMap.getNodes(),
				myPos, fullMap);

		// Assert

		assertThat(target, is(shouldBeTarget));

	}

}
