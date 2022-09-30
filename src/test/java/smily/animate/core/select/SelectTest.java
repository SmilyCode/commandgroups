package smily.animate.core.select;

import org.bukkit.command.CommandSender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import smily.animate.core.command.CommandGroup;

import static org.junit.jupiter.api.Assertions.*;

class SelectTest {
    @Mock
    CommandSender sender1;
    @Mock
    CommandGroup commandGroup1;
    @Mock
    CommandGroup commandGroup2;

    Select<CommandGroup> select = new Select<>();

    @BeforeEach
    public void before(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testStoreSelected(){
        select.storeSelected(sender1, commandGroup1);
        select.storeSelected(sender1, commandGroup2);

        assertEquals(commandGroup2, select.getSelected(sender1));
    }
}