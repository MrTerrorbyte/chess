package tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ BishopTest.class, BoardTest.class, CannonTest.class,
		ChessPieceTest.class, KingTest.class, KnightTest.class,
		LeaperTest.class, PawnTest.class, QueenTest.class, RookTest.class })
public class AllTests {

}
