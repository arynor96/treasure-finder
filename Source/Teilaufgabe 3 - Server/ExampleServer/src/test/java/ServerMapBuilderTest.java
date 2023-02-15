
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import map.Coordinate;
import map.EMapShape;
import map.ServerFullMap;
import map.ServerMapBuilder;
import map.ServerMapNode;
import messagesBase.messagesFromClient.PlayerHalfMap;
import server.main.converters.PlayerHalfMapConverter;

public class ServerMapBuilderTest {

	private ServerFullMap map1;
	private ServerFullMap map2;

	@BeforeAll
	public static void setUpBeforeTest() {

	}

	@AfterAll
	public static void tearDownAfterTest() {

	}

	@BeforeEach
	public void setUp() {

		PlayerHalfMap halfMap1 = PlayerHalfMapCreator.createValidMap();
		PlayerHalfMap halfMap2 = PlayerHalfMapCreator.createValidMap();

		map1 = PlayerHalfMapConverter.toLocal(halfMap1);
		map2 = PlayerHalfMapConverter.toLocal(halfMap2);

	}

	@AfterEach
	public void tearUp() {

	}

	@Test
	public void GivenTwoHalfMaps_BuildAFullRandomPosMap_ExpectedCorrectSize() {

		ServerFullMap fullMap = ServerMapBuilder.mergeMaps(this.map1, this.map2);
		EMapShape shape = fullMap.getMapShape();

		int expected_x = 0;
		int expected_y = 0;

		if (shape == EMapShape.RECTANGLE) {
			expected_x = 19;
			expected_y = 4;
		} else {
			expected_x = 9;
			expected_y = 9;

		}

		Set<Map.Entry<Coordinate, ServerMapNode>> mapNodes = fullMap.getNodes().entrySet();
		int max_x = 0;
		int max_y = 0;

		for (Map.Entry<Coordinate, ServerMapNode> entry : mapNodes) {
			if (entry.getKey().getX() > max_x)
				max_x = entry.getKey().getX();
			if (entry.getKey().getY() > max_y)
				max_y = entry.getKey().getY();
		}

		assertThat(max_x, is(equalTo(expected_x)));
		assertThat(max_y, is(equalTo(expected_y)));
		assertThat(mapNodes.size(), is(equalTo(100)));

	}

}
