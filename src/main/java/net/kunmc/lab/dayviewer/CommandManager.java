package net.kunmc.lab.dayviewer;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CommandManager implements CommandExecutor, TabCompleter {
    private final String prefixAccept = ChatColor.GREEN + "[DayViewer]" + ChatColor.RESET + " ";
    private final String prefixReject = ChatColor.RED + "[DayViewer]" + ChatColor.RESET + " ";

    private boolean same(String a, String b) {
        return a.equals(b);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length <= 0)
            return false;
        // start
        if (same(args[0], "start")) {
            DayViewer.plugin.start();
            sender.sendMessage(prefixAccept + "Plugin is started.");
            return true;
        }

        // resume
        if (same(args[0], "resume")) {
            DayViewer.plugin.resume();
            sender.sendMessage(prefixAccept + "Plugin is resumed.");
            return true;
        }

        // stop
        if (same(args[0], "stop")) {
            DayViewer.plugin.stop();
            sender.sendMessage(prefixAccept + "Plugin is stopped.");
            return true;
        }

        // first-day <int>
        if (args.length == 2 && same(args[0], "first-day")) {
            try {
                int value = Integer.parseInt(args[1]);
                if (DayViewer.plugin.config.setFirstDay(value)) {
                    sender.sendMessage(String.format("%s\"First Day\" が %s に設定されました。", prefixAccept, args[1]));
                    return true;
                }
            } catch (Exception ignore) {
                // do nothing
            }

            sender.sendMessage(prefixReject + " 不正な引数です。");
            return false;
        }

        // last-day <int>
        if (args.length == 2 && same(args[0], "last-day")) {
            try {
                int value = Integer.parseInt(args[1]);
                if (DayViewer.plugin.config.setLastDay(value)) {
                    sender.sendMessage(String.format("%s\"Last Day\" が %s に設定されました。", prefixAccept, args[1]));
                    return true;
                }
            } catch (Exception ignore) {
                // do nothing
            }

            sender.sendMessage(prefixReject + "不正な引数です。");
            return false;
        }

        // day-min <double>
        if (args.length == 2 && same(args[0], "day-min")) {
            try {
                double value = Double.parseDouble(args[1]);
                if (DayViewer.plugin.config.setDayMin(value)) {
                    sender.sendMessage(String.format("%s\"一日あたりの分数\" が %s に設定されました。", prefixAccept, args[1]));
                    return true;
                }
            } catch (Exception ignore) {
                // do nothing
            }

            sender.sendMessage(prefixReject + "不正な引数です。");
            return false;
        }

        // loadconfig
        if (same(args[0], "loadconfig")) {
            if (DayViewer.plugin.config.loadConfig()) {
                sender.sendMessage(prefixAccept + "Config is loaded.");
                return true;
            }
            sender.sendMessage(prefixReject + "Failed to load config.");
        }

        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            return Stream.of("loadconfig", "start", "stop", "resume", "first-day", "last-day", "day-min")
                    .filter(e -> e.startsWith(args[0]))
                    .collect(Collectors.toList());
        }

        // first-day
        if (args.length == 2 && same(args[0], "first-day")) {
            if (args[1].length() == 0) {
                String suggestion = String.valueOf(DayViewer.plugin.config.getFirstDay());
                return Collections.singletonList(suggestion);
            }
        }

        // last-day
        if (args.length == 2 && same(args[0], "last-day")) {
            if (args[1].length() == 0) {
                String suggestion = String.valueOf(DayViewer.plugin.config.getLastDay());
                return Collections.singletonList(suggestion);
            }
        }

        // day-min
        if (args.length == 2 && same(args[0], "day-min")) {
            if (args[1].length() == 0) {
                String suggestion = String.valueOf(DayViewer.plugin.config.getDayMin());
                return Collections.singletonList(suggestion);
            }
        }

        return null;
    }
}