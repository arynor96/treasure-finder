import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import gameinfo.EMyMapPosition;
import gameinfo.MapAnalyser;
import halfmap.ClientHalfMapGenerator;
import map.ClientFullMap;
import map.ClientMapNode;
import map.Coordinate;

public class MapAnalyserTest {

	private ClientFullMap halfMap1 = new ClientFullMap();
	private ClientFullMap halfMap2 = new ClientFullMap();
	private ClientFullMap fullMap = new ClientFullMap();
	private MapAnalyser analyser = new MapAnalyser();

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
		analyser = new MapAnalyser();
	}

	// Positive test.
	@Test
	public void GivenOneInitialisedAnalyser_VerifyingClientMapPosition_ExpectedRightPosition() {

		// Arrange
		analyser.updateMapAnalyser(fullMap);
		analyser.setMyMapPosition();

		// Act
		EMyMapPosition position = analyser.getRelevantPositions().getMyMapPosition();

		// Assert
		assertThat(EMyMapPosition.RIGHT, is(equalTo(position)));

	}

	@Test
	public void GivenOneInitialisedAnalyser_FindWhereIsMyTreasure_ExpectedCorrectTreasurePosition() {

		// Arrange
		analyser.updateMapAnalyser(fullMap);
		analyser.setMyMapPosition();

		// treasure is set in FullMapBuilder class.
		Coordinate treasureArrangePosition = new Coordinate(15, 2);
		// Act

		Coordinate treasurePosition = analyser.getRelevantPositions().getTreasurePosition();

		// Assert
		assertThat(treasureArrangePosition, is(equalTo(treasurePosition)));

	}

	// Positive test
	@Test
	public void GivenOneInitialisedAnalyser_FindWhereIsMyTreasure_ExpectedNoTreasurePosition() {

		// Arrange

		Set<Map.Entry<Coordinate, ClientMapNode>> fullMapNodes = fullMap.getNodes().entrySet();
		Map<Coordinate, ClientMapNode> fullMapNodes2 = new HashMap<Coordinate, ClientMapNode>();

		for (Map.Entry<Coordinate, ClientMapNode> entry : fullMapNodes) {
			fullMapNodes2.put(new Coordinate(entry.getKey().getX(), entry.getKey().getY()),
					new ClientMapNode(entry.getValue().getTerrainType(), entry.getValue().getFortState(),
							entry.getValue().getPlayerState(), false));

		}

		ClientFullMap fullMap2 = new ClientFullMap(fullMapNodes2);

		analyser.updateMapAnalyser(fullMap2);
		analyser.setMyMapPosition();

		// Act
		Coordinate treasurePosition = analyser.getRelevantPositions().getTreasurePosition();

		// Assert
		assertThat(treasurePosition, is(nullValue()));

	}

}
