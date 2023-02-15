
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import exceptions.HalfMapFortException;
import exceptions.HalfMapIslandException;
import exceptions.HalfMapNotEnoughTerrainTypeException;
import exceptions.HalfMapSizeException;
import exceptions.HalfMapWaterOnEdgeException;
import game.GameManager;
import messagesBase.UniqueGameIdentifier;
import messagesBase.messagesFromClient.PlayerHalfMap;
import rules.HalfMapFortExistsOnGrass;
import rules.HalfMapIsland;
import rules.HalfMapSize;
import rules.HalfMapTerrainType;
import rules.HalfMapWaterOnEdges;

public class PlayerHalfMapValidationTest {

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
	public void GivenOneHalfMap_TestForIsland_ExpectedIslandFound() {

		// Arrange
		HalfMapIsland halfMapIsland = new HalfMapIsland();
		PlayerHalfMap halfMapWithIsland = PlayerHalfMapCreator.createIslandMap();
		// PlayerHalfMap halfMapWithIsland = PlayerHalfMapCreator.createValidMap();

		// Act

		Executable checkIslandExists = () -> {
			halfMapIsland.checkMapRules(new UniqueGameIdentifier("tesID"), halfMapWithIsland, new GameManager());
		};

		// Assert
		Assertions.assertThrows(HalfMapIslandException.class, checkIslandExists);

	}

	@Test
	public void GivenOneHalfMap_TestForIsland_ExpectedIslandNotFound() {

		// Arrange
		HalfMapIsland halfMapIsland = new HalfMapIsland();

		PlayerHalfMap halfMapWithoutIsland = PlayerHalfMapCreator.createValidMap();

		// Act

		Executable checkIslandExists = () -> {
			halfMapIsland.checkMapRules(new UniqueGameIdentifier("tesID"), halfMapWithoutIsland, new GameManager());
		};

		// Assert
		Assertions.assertDoesNotThrow(checkIslandExists);

	}

	@Test
	public void GivenOneHalfMap_TestForFortOnGrass_ExpectedFortNotOnGrass() {

		// Arrange
		HalfMapFortExistsOnGrass ruleFortOnGrass = new HalfMapFortExistsOnGrass();
		PlayerHalfMap halfMapNoFort = PlayerHalfMapCreator.createFortOnMountainMap();

		// Act

		Executable checkFortExistsOnGrass = () -> {
			ruleFortOnGrass.checkMapRules(new UniqueGameIdentifier("tesID"), halfMapNoFort, new GameManager());
		};

		// Assert
		Assertions.assertThrows(HalfMapFortException.class, checkFortExistsOnGrass);

	}

	@Test
	public void GivenOneHalfMap_TestForFortExistence_ExpectedFortNotPresent() {

		// Arrange
		HalfMapFortExistsOnGrass ruleFortOnGrass = new HalfMapFortExistsOnGrass();
		PlayerHalfMap halfMapNoFort = PlayerHalfMapCreator.createNoFortMap();

		// Act

		Executable checkFortExistsOnGrass = () -> {
			ruleFortOnGrass.checkMapRules(new UniqueGameIdentifier("tesID"), halfMapNoFort, new GameManager());
		};

		// Assert
		Assertions.assertThrows(HalfMapFortException.class, checkFortExistsOnGrass);

	}

	@Test
	public void GivenOneHalfMap_TestForFortOnGrass_ExpectedFortExistsOnGrass() {

		// Arrange
		HalfMapFortExistsOnGrass ruleFortOnGrass = new HalfMapFortExistsOnGrass();
		PlayerHalfMap halfMapValid = PlayerHalfMapCreator.createValidMap();

		// Act

		Executable checkFortOnGrass = () -> {
			ruleFortOnGrass.checkMapRules(new UniqueGameIdentifier("tesID"), halfMapValid, new GameManager());
		};

		// Assert

		Assertions.assertDoesNotThrow(checkFortOnGrass);

	}

	@Test
	public void GivenOneHalfMap_TestForTerrainType_ExpectedEnoughTerrainType() {

		// Arrange
		HalfMapTerrainType ruleTerrainType = new HalfMapTerrainType();
		PlayerHalfMap halfMapFewMountain = PlayerHalfMapCreator.createValidMap();

		// Act

		Executable checkTerrainType = () -> {
			ruleTerrainType.checkMapRules(new UniqueGameIdentifier("tesID"), halfMapFewMountain, new GameManager());
		};

		// Assert
		Assertions.assertDoesNotThrow(checkTerrainType);

	}

	@Test
	public void GivenOneHalfMap_TestForTerrainType_ExpectedNotEnoughMountainException() {

		// Arrange
		HalfMapTerrainType ruleTerrainType = new HalfMapTerrainType();
		PlayerHalfMap halfMapFewMountain = PlayerHalfMapCreator.createNotEnoughMountainMap();

		// Act

		Executable checkTerrainType = () -> {
			ruleTerrainType.checkMapRules(new UniqueGameIdentifier("tesID"), halfMapFewMountain, new GameManager());
		};

		// Assert
		Assertions.assertThrows(HalfMapNotEnoughTerrainTypeException.class, checkTerrainType);

	}

	@Test
	public void GivenOneHalfMap_TestForTerrainType_ExpectedNotEnoughWaterException() {

		// Arrange
		HalfMapTerrainType ruleTerrainType = new HalfMapTerrainType();
		PlayerHalfMap halfMapFewWater = PlayerHalfMapCreator.createNotEnoughWater();

		// Act

		Executable checkTerrainType = () -> {
			ruleTerrainType.checkMapRules(new UniqueGameIdentifier("tesID"), halfMapFewWater, new GameManager());
		};

		// Assert
		Assertions.assertThrows(HalfMapNotEnoughTerrainTypeException.class, checkTerrainType);

	}

	@Test
	public void GivenOneHalfMap_TestForTerrainType_ExpectedNotEnoughGrass() {

		// Arrange
		HalfMapTerrainType ruleTerrainType = new HalfMapTerrainType();
		PlayerHalfMap halfMapFewGrass = PlayerHalfMapCreator.createNotEnoughGrass();

		// Act

		Executable checkTerrainType = () -> {
			ruleTerrainType.checkMapRules(new UniqueGameIdentifier("tesID"), halfMapFewGrass, new GameManager());
		};

		// Assert
		Assertions.assertThrows(HalfMapNotEnoughTerrainTypeException.class, checkTerrainType);

	}

	@Test
	public void GivenOneHalfMap_TestForMapSize_ExpectedCorrectSize() {

		// Arrange
		HalfMapSize ruleSize = new HalfMapSize();
		PlayerHalfMap halfMapValid = PlayerHalfMapCreator.createValidMap();

		// Act

		Executable checkSizeRule = () -> {
			ruleSize.checkMapRules(new UniqueGameIdentifier("tesID"), halfMapValid, new GameManager());
		};

		// Assert
		Assertions.assertDoesNotThrow(checkSizeRule);
		// Assertions.assertThrows(null, null)

	}

	@Test
	public void GivenOneHalfMap_TestForMapSize_ExpectedSizeException() {
		// Arrange

		HalfMapSize ruleSize = new HalfMapSize();
		PlayerHalfMap halfMapWrongSize = PlayerHalfMapCreator.createTooSmallMap();

		// Act

		Executable checkSizeRule = () -> {
			ruleSize.checkMapRules(new UniqueGameIdentifier("tesID"), halfMapWrongSize, new GameManager());
		};

		// Assert
		// Assertions.assertDoesNotThrow(checkSizeRule);
		Assertions.assertThrows(HalfMapSizeException.class, checkSizeRule);

	}

	@Test
	public void GivenOneHalfMap_TestForWaterOnEdge_ExpectedWaterOnEdgeException() {
		// Arrange

		HalfMapWaterOnEdges waterOnEdgesRule = new HalfMapWaterOnEdges();
		PlayerHalfMap halfMapTooMuchOnEdge = PlayerHalfMapCreator.createTooMuchWaterEdge();

		// Act

		Executable checkSizeRule = () -> {
			waterOnEdgesRule.checkMapRules(new UniqueGameIdentifier("tesID"), halfMapTooMuchOnEdge, new GameManager());
		};

		// Assert
		// Assertions.assertDoesNotThrow(checkSizeRule);
		Assertions.assertThrows(HalfMapWaterOnEdgeException.class, checkSizeRule);

	}

	@Test
	public void GivenOneHalfMap_TestForWaterOnEdge_ExpectedNoThrow() {
		// Arrange

		HalfMapWaterOnEdges waterOnEdgesRule = new HalfMapWaterOnEdges();
		PlayerHalfMap halfMapValid = PlayerHalfMapCreator.createValidMap();

		// Act

		Executable checkSizeRule = () -> {
			waterOnEdgesRule.checkMapRules(new UniqueGameIdentifier("tesID"), halfMapValid, new GameManager());
		};

		// Assert
		// Assertions.assertDoesNotThrow(checkSizeRule);
		Assertions.assertDoesNotThrow(checkSizeRule);

	}

}
