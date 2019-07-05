package gmail.fopypvp174.cmtags;

import gmail.fopypvp174.cmtags.config.Config;
import gmail.fopypvp174.cmtags.events.PermissionsEvent;
import gmail.fopypvp174.cmtags.events.SimpleClansEvent;
import gmail.fopypvp174.cmtags.objects.Grupos;
import net.sacredlabyrinth.phaed.simpleclans.SimpleClans;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;

public class CmTags extends JavaPlugin {

    private Config config;
    private ArrayList<Grupos> grupos = new ArrayList<>();

    @Override
    public void onEnable() {
        config = new Config(this, "configurar.yml");
        if (getServer().getPluginManager().isPluginEnabled("SimpleClans")) {
            getServer().getPluginManager().registerEvents(new SimpleClansEvent(this), this);
        }
        if (getServer().getPluginManager().isPluginEnabled("PermissionsEx")) {
            getServer().getPluginManager().registerEvents(new PermissionsEvent(this), this);
        }
        pegarInfo();
        atualizarScore();
    }

    @Override
    public void onDisable() {
        config.reloadConfig();
        config.saveConfig();
    }

    private void pegarInfo() {
        config.getCustomConfig().getConfigurationSection("Tags").getKeys(false).forEach(grupoName -> {
            grupos.add(new Grupos(grupoName,
                    config.getCustomConfig().getString("Tags." + grupoName + ".Permissao"),
                    config.getCustomConfig().getString("Tags." + grupoName + ".Preffix"),
                    config.getCustomConfig().getString("Tags." + grupoName + ".Suffix"),
                    config.getCustomConfig().getInt("Tags." + grupoName + ".Priority")));
        });
    }


    private String fazerTeam(Player player, Grupos grupo) {
        StringBuilder nomeGrupo = new StringBuilder(4 + grupo.getNome().length());
        double priority = grupo.getPrioridade();

        SimpleClans sc = SimpleClans.getInstance();

        String tagClan = "";

        if (config.getCustomConfig().getBoolean("Organizar_por_tag")) {
            int tamanhoConfig = sc.getSettingsManager().getTagMaxLength();
            for (int i = 0; i < tamanhoConfig; i++) {
                tagClan += "Z";
            }

            if (sc.getClanManager().getClanPlayer(player) != null) {
                tagClan = sc.getClanManager().getClanPlayer(player).getClan().getTag();

                int tamanhoTag = sc.getClanManager().getClanPlayer(player).getClan().getTag().length();

                for (int i = 0; i < (tamanhoConfig - tamanhoTag); i++) {
                    tagClan += "Z";
                }

            }
        }

        int casasPriority = 1;
        while ((priority = priority / 10) > 1) {
            casasPriority++;
        }
        for (int i = 0; i < (2 - casasPriority); i++) {
            nomeGrupo.append("0");
        }

        nomeGrupo.append(grupo.getPrioridade()).append(tagClan).append(player.getName());

        if (nomeGrupo.length() > 16) {
            nomeGrupo.substring(0, 16);
        }

        return nomeGrupo.toString();
    }

    @Deprecated
    public void atualizarScore() {
        for (Player all : Bukkit.getOnlinePlayers()) {

            Scoreboard scoreboard = all.getScoreboard();

            for (Player all2 : Bukkit.getOnlinePlayers()) {

                for (Grupos grupo : grupos) {
                    if (all2.hasPermission(grupo.getPermissao())) {

                        String teamName = fazerTeam(all2, grupo);
                        String preffix = ChatColor.translateAlternateColorCodes('&', grupo.getPreffix());
                        String suffix = getClan(all2, grupo);

                        Team team = scoreboard.getTeam(teamName);

                        if (team == null) {
                            team = scoreboard.registerNewTeam(teamName);
                        }

                        if (preffix.length() > 16) {
                            team.setPrefix(preffix.substring(0, 16));
                        } else {
                            team.setPrefix(preffix);
                        }

                        if (suffix.length() > 16) {
                            team.setSuffix(suffix.substring(0, 16));
                        } else {
                            team.setSuffix(suffix);
                        }

                        team.addPlayer(all2);
                        break;
                    }
                }

            }
            all.setScoreboard(scoreboard);
        }

    }

    private String getClan(Player player, Grupos grupo) {
        if (getServer().getPluginManager().getPlugin("SimpleClans") != null) {
            SimpleClans sc = SimpleClans.getInstance();
            if (sc.getClanManager().getClanPlayer(player) != null) {
                String tagClan = sc.getClanManager().getClanPlayer(player).getClan().getColorTag();
                String tagConfig = config.getCustomConfig().getString("Tags." + grupo.getNome() +
                        ".Suffix").replace("%clan%", tagClan);
                String suffix = ChatColor.translateAlternateColorCodes('&', tagConfig);
                return "§r " + suffix;
            }
        }
        return "§r";
    }
}