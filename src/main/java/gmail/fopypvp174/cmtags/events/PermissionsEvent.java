package gmail.fopypvp174.cmtags.events;

import gmail.fopypvp174.cmtags.CmTags;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import ru.tehkode.permissions.events.PermissionEntityEvent;
import ru.tehkode.permissions.events.PermissionSystemEvent;

public class PermissionsEvent implements Listener {

    private CmTags cmTags;

    public PermissionsEvent(CmTags cmTags) {
        this.cmTags = cmTags;
    }

    @Deprecated
    @EventHandler
    public void permissionsEvent(PermissionSystemEvent e) {
        cmTags.atualizarScore();
    }

    @Deprecated
    @EventHandler
    public void permissionsEvent(PermissionEntityEvent e) {
        cmTags.atualizarScore();
    }


    @Deprecated
    @EventHandler
    public void playerJoin(PlayerJoinEvent e) {
        cmTags.atualizarScore();
    }
}
