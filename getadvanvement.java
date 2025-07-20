import com.fren_gor.ultimateAdvancementAPI.advancement.BaseAdvancement;
import com.fren_gor.ultimateAdvancementAPI.advancement.AdvancementTab;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.entity.Player;

public class AdvancementUtils {

    public static void showNextUnlockableAdvancement(AdvancementTab tab, Player player) {
        if (!tab.isActive()) return;

        for (BaseAdvancement advancement : tab.getAdvancementsWithoutRoot()) {
            if (advancement.isGranted(player)) continue;

            boolean allParentsGranted = true;
            for (BaseAdvancement parent : advancement.getParents()) {
                if (!parent.isGranted(player)) {
                    allParentsGranted = false;
                    break;
                }
            }

            if (allParentsGranted) {
                // Name (Titel) und Beschreibung holen
                Component title = advancement.getDisplay().getTitle();
                Component description = advancement.getDisplay().getDescription();

                // In String umwandeln (für Chat)
                String titleStr = MiniMessage.miniMessage().serialize(title);
                String descStr = MiniMessage.miniMessage().serialize(description);

                // An den Spieler senden
                player.sendMessage(Component.text("§aNächstes Ziel: ").append(title));
                player.sendMessage(description);

                return; // Nur eins anzeigen
            }
        }

        // Kein freischaltbares Advancement gefunden
        player.sendMessage(Component.text("§cDu hast aktuell kein neues freischaltbares Ziel."));
    }
}
