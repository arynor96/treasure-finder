
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ServerAdministration.GameIDGenerator;
import game.GameID;

public class GameIDGeneratorTest {

	@BeforeAll
	public static void setUpBeforeTest() {

	}

	@AfterAll
	public static void tearDownAfterTest() {

	}

	@BeforeEach
	public void setUp() {

	}

	@AfterEach
	public void tearUp() {

	}

	@Test
	public void GivenOneGenerator_Generate50GameIDs_Expect50DifferentIDs() {
		Set<GameID> gameIDs = new HashSet<>();

		for (int i = 0; i < 50; i++) {
			gameIDs.add(GameIDGenerator.generate(gameIDs));
		}

		assertThat(gameIDs.size(), is(equalTo(50)));

	}

}
