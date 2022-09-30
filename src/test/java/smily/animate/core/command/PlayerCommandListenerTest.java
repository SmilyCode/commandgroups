package smily.animate.core.command;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Set;

class PlayerCommandListenerTest {

    @BeforeEach
    public void beforeEach(){
        MockitoAnnotations.initMocks(this);
        playerCommandPreprocessEvent = new PlayerCommandPreprocessEvent(player, "/kill SmilyKuy", players);
    }

    @Mock
    Player player;

    @Mock
    Set<Player> players;

    PlayerCommandPreprocessEvent playerCommandPreprocessEvent;
    PlayerCommandListener playerCommandListener = new PlayerCommandListener();

    @Test
    public void canListenForInputedCommand(){
        playerCommandListener.listenForCommand(playerCommandPreprocessEvent);
    }
}