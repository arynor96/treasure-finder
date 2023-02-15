import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.util.stream.Stream;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import gameinfo.MapAnalyser;
import halfmap.ClientHalfMapGenerator;
import map.ClientFullMap;
import map.ClientMapNode;
import map.Coordinate;
import map.EFort;
import map.ETerrainClient;
import movement.MoveManager;
import movement.targetfinder.FortFinder;
import movement.targetfinder.ITargetFinder;

public class MoveManagerTest {

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
	}

	// Positive test
	@Test
	public void GivenOneMap_VerifyingIfTreasureIsCollected_ExpectedChangeStrategy() {

		MoveManager moveManager = new MoveManager(this.fullMap);

		MapAnalyser mapAnalyser = new MapAnalyser();
		mapAnalyser.updateMapAnalyser(this.fullMap);

		moveManager.updateMoveManagerPositions(mapAnalyser.getRelevantPositions(), true);
		moveManager.getNextMove();

		ITargetFinder targetFinder = moveManager.getTargetFinder();
		assertThat(targetFinder, instanceOf(FortFinder.class));

	}

	// Positive test
	@Test
	public void GivenOneMapWithFortVisible_CallingNextMoveInFortFinder_ExpectedSteps() {

		// Arrange
		ClientMapNode fortNode = this.fullMap.getNodes().get(new Coordinate(1, 1));
		fortNode.setTerrainType(ETerrainClient.GRASS);
		fortNode.setFortState(EFort.ENEMYFORT);

		MoveManager moveManager = new MoveManager(this.fullMap);
		MapAnalyser mapAnalyser = new MapAnalyser();
		mapAnalyser.updateMapAnalyser(this.fullMap);
		moveManager.updateMoveManagerPositions(mapAnalyser.getRelevantPositions(), true);

		// In FullMapBuilder my position is: 14,2
		// Fort position is 1,1
		// I should be able to get at least 10 moves from FortFinder.
		for (int i = 0; i < 10; i++) {
			assertDoesNotThrow(() -> moveManager.getNextMove());
		}

	}

	@ParameterizedTest
	@MethodSource("provideNewFullMaps")
	public void GivenOneMap_AbleToFindANewTarget_ExpectedANewTarget(ClientFullMap map) {

		MapAnalyser mapAnalyser = new MapAnalyser();
		mapAnalyser.updateMapAnalyser(map);

		MoveManager moveManager = new MoveManager(map);

		moveManager.updateMoveManagerPositions(mapAnalyser.getRelevantPositions(), false);

		// I should always be able to get at least 10 new moves on any new random
		// generated map with no treasure visible.
		// Next move can be seen in the console using the logger.
		for (int i = 0; i < 10; i++) {
			assertDoesNotThrow(() -> moveManager.getNextMove());
		}

	}

	private static Stream<Arguments> provideNewFullMaps() {
		return Stream.of(
				Arguments.of(FullMapBuilder.mergeMapsTreasureNotVisible(ClientHalfMapGenerator.generateHalfMap(),
						ClientHalfMapGenerator.generateHalfMap())),
				Arguments.of(FullMapBuilder.mergeMapsTreasureNotVisible(ClientHalfMapGenerator.generateHalfMap(),
						ClientHalfMapGenerator.generateHalfMap())),
				Arguments.of(FullMapBuilder.mergeMapsTreasureNotVisible(ClientHalfMapGenerator.generateHalfMap(),
						ClientHalfMapGenerator.generateHalfMap())),
				Arguments.of(FullMapBuilder.mergeMapsTreasureNotVisible(ClientHalfMapGenerator.generateHalfMap(),
						ClientHalfMapGenerator.generateHalfMap())));

	}

}
