package sc.plugin2020

import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldNotBeSameInstanceAs
import io.kotest.core.spec.style.StringSpec
import sc.framework.plugins.Player

class CloneTest: StringSpec({
    "clone Player" {
        val player = Player(Team.RED, "aPlayer")
        val clone = player.clone()
        clone shouldBe player
        clone shouldNotBeSameInstanceAs player
    }
    "clone Board" {
        val board = Board()
        board.getField(0, 0, 0).pieces.add(Piece(Team.RED, PieceType.BEETLE))
        val clone = board.clone()
        clone shouldBe board
        clone shouldNotBeSameInstanceAs board
        clone.getField(0, 0, 0) shouldNotBeSameInstanceAs board.getField(0, 0, 0)
        // note that the individual pieces are immutable and don't need to be cloned, only the stack which holds them
        clone.getField(0, 0, 0).pieces shouldNotBeSameInstanceAs board.getField(0, 0, 0).pieces
    }
    "clone GameState" {
        val state = GameState(second = Player(Team.BLUE, "aBluePlayer"), turn = 5)
        val clone = state.clone()
        clone shouldBe state
        clone shouldNotBeSameInstanceAs state
        clone.getDeployedPieces(Team.RED) shouldBe state.getDeployedPieces(Team.RED)
        clone.currentTeam shouldBe state.currentTeam
        clone.currentPlayer shouldNotBeSameInstanceAs state.currentPlayer
        clone.lastMove shouldBe state.lastMove
        clone.board shouldBe state.board
    }
    "clone another Game state" {
        val state = Game().gameState
        state.turn++
        state.currentTeam shouldBe Team.BLUE
        val clone = GameState(state)
        clone shouldBe state
        clone.turn shouldBe 1
        clone.currentTeam shouldBe state.currentTeam
    }
})