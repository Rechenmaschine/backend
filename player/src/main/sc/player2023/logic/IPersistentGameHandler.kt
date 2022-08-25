package sc.player2023.logic

import sc.player.IGameHandler

/**
 * Das Interface für persistente Logik. Die Logik kann über mehrere Spiele
 * beibehalten werden und Objekte werden nicht gelöscht.
 */
interface IPersistentGameHandler: IGameHandler {
    
    /**
     * Setzt die Logik zurück. Wird nach jedem Spiel aufgerufen.
     */
    fun reset()
    
}