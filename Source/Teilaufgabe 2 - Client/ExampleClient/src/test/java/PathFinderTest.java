import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.util.Map;
import java.util.Set;
import java.util.Stack;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import exceptions.NewTargetIsWaterException;
import halfmap.ClientHalfMapGenerator;
import map.ClientFullMap;
import map.ClientMapNode;
import map.Coordinate;
import map.ETerrainClient;
import movement.PathFinder;

public class PathFinderTest {

	private ClientFullMap halfMap1 = new ClientFullMap();
	private ClientFullMap halfMap2 = new ClientFullMap();
	private ClientFullMap fullMap = new ClientFullMap();
	private Coordinate target = new Coordinate(0, 0);
	private Coordinate myPos = new Coordinate(0, 0);

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

	// Positive test
	@Test
	public void GivenTwoCloseNodes_BuildingPathBetweenThem_ExpectAPathWithLenght1() {

		// Arrange
		Map<Coordinate, ClientMapNode> nodes = fullMap.getNodes();
		Coordinate myPos = new Coordinate(3, 1);
		Coordinate target = new Coordinate(4, 1);

		// to make sure that target and my positions are not water.
		nodes.get(myPos).setTerrainType(ETerrainClient.GRASS);
		nodes.get(target).setTerrainType(ETerrainClient.GRASS);

		PathFinder pathFinder = new PathFinder(fullMap);
		// Act

		Stack<Coordinate> path = pathFinder.calculatePath(myPos, target);

		// Assert

		assertThat(path.size(), is(equalTo(1)));

	}

	// Negative test
	@Test
	public void GivenOneNode_BuildingPathToIt_ExpectTargetIsWaterException() {

		// Arrange

		Set<Map.Entry<Coordinate, ClientMapNode>> fullMapNodes = fullMap.getNodes().entrySet();
		for (Map.Entry<Coordinate, ClientMapNode> entry : fullMapNodes) {
			if (entry.getValue().getTerrainType() == ETerrainClient.WATER) {
				target = entry.getKey();
			} else {
				myPos = entry.getKey();
			}
		}

		PathFinder pathFinder = new PathFinder(fullMap);
		// Act

		Executable path = () -> {
			pathFinder.calculatePath(myPos, target);
		};

		// Assert
		Assertions.assertThrows(NewTargetIsWaterException.class, path);

	}

}
