package net.leonardo_dgs.pluginlib;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.leonardo_dgs.pluginlib.reflect.NmsVersion;
import net.leonardo_dgs.pluginlib.reflect.ServerReflection;
import net.leonardo_dgs.pluginlib.reflect.ServerVersion;
import net.leonardo_dgs.pluginlib.reflect.ServerVersions;
import net.leonardo_dgs.pluginlib.util.Text;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Stream;

public final class Players {

    private static final Object OPENBOOK_PACKET;

    private static final Object TITLE_ENUM;
    private static final Object SUBTITLE_ENUM;
    private static final Constructor<?> TITLE_CONSTRUCTOR;
    private static final Method ICHATBASECOMPONENT_A_METHOD;

    private static final Constructor<?> TABLIST_CONSTRUCTOR;

    private static final Object ACTIONBAR_ENUM;
    private static final Constructor<?> ACTIONBAR_CONSTRUCTOR;

    static
    {
        Object title_Enum;
        Object subtitle_Enum;
        Constructor<?> title_Constructor;
        Method iChatBaseComponent_A_Method;
        Constructor<?> tablist_Constructor;
        Object actionbar_Enum;
        Constructor<?> actionbar_Constructor;
        try
        {
            title_Enum = ServerReflection.nmsClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("TITLE").get(null);
            subtitle_Enum = ServerReflection.nmsClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("SUBTITLE").get(null);
            title_Constructor = ServerReflection.nmsClass("PacketPlayOutTitle").getConstructor(ServerReflection.nmsClass("PacketPlayOutTitle").getDeclaredClasses()[0], ServerReflection.nmsClass("IChatBaseComponent"), int.class, int.class, int.class);
            iChatBaseComponent_A_Method = ServerReflection.nmsClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", String.class);
            tablist_Constructor = ServerReflection.nmsClass("PacketPlayOutPlayerListHeaderFooter").getConstructor(ServerReflection.nmsClass("IChatBaseComponent"));
            actionbar_Enum = ServerReflection.nmsClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("ACTIONBAR").get(null);
            actionbar_Constructor = ServerReflection.nmsClass("PacketPlayOutTitle").getConstructor(ServerReflection.nmsClass("PacketPlayOutTitle").getDeclaredClasses()[0], ServerReflection.nmsClass("IChatBaseComponent"));
        }
        catch (IllegalAccessException | NoSuchFieldException | NoSuchMethodException e)
        {
            e.printStackTrace();
            title_Enum = null;
            subtitle_Enum = null;
            title_Constructor = null;
            iChatBaseComponent_A_Method = null;
            tablist_Constructor = null;
            actionbar_Enum = null;
            actionbar_Constructor = null;
        }
        TITLE_ENUM = title_Enum;
        SUBTITLE_ENUM = subtitle_Enum;
        TITLE_CONSTRUCTOR = title_Constructor;
        ICHATBASECOMPONENT_A_METHOD = iChatBaseComponent_A_Method;
        TABLIST_CONSTRUCTOR = tablist_Constructor;
        ACTIONBAR_ENUM = actionbar_Enum;
        ACTIONBAR_CONSTRUCTOR = actionbar_Constructor;

        Object openBook_Packet = null;
        try
        {
            Constructor<?> packetConstructor;
            Enum<?> enumHand;
            Object packetDataSerializer;
            Object packetDataSerializerArg;
            Object minecraftKey;
            switch (NmsVersion.getCurrent())
            {
                case v1_14_R1:
                    enumHand = (Enum<?>) ServerReflection.nmsClass("EnumHand").getField("MAIN_HAND").get(null);
                    packetConstructor = ServerReflection.nmsClass("PacketPlayOutOpenBook").getConstructor(ServerReflection.nmsClass("EnumHand"));
                    openBook_Packet = packetConstructor.newInstance(enumHand);
                    break;

                case v1_13_R2:
                case v1_13_R1:
                    enumHand = (Enum<?>) ServerReflection.nmsClass("EnumHand").getField("MAIN_HAND").get(null);
                    minecraftKey = ServerReflection.nmsClass("MinecraftKey").getMethod("a", String.class).invoke(null, "minecraft:book_open");
                    packetDataSerializerArg = ServerReflection.nmsClass("PacketDataSerializer").getConstructor(ByteBuf.class).newInstance(Unpooled.buffer());
                    packetDataSerializer = ServerReflection.nmsClass("PacketDataSerializer").getMethod("a", Enum.class).invoke(packetDataSerializerArg, enumHand);
                    packetConstructor = ServerReflection.nmsClass("PacketPlayOutCustomPayload").getConstructor(ServerReflection.nmsClass("MinecraftKey"), ServerReflection.nmsClass("PacketDataSerializer"));
                    openBook_Packet = packetConstructor.newInstance(minecraftKey, packetDataSerializer);
                    break;

                case v1_12_R1:
                case v1_11_R1:
                case v1_10_R1:
                case v1_9_R2:
                case v1_9_R1:
                    enumHand = (Enum<?>) ServerReflection.nmsClass("EnumHand").getField("MAIN_HAND").get(null);
                    packetDataSerializerArg = ServerReflection.nmsClass("PacketDataSerializer").getConstructor(ByteBuf.class).newInstance(Unpooled.buffer());
                    packetDataSerializer = ServerReflection.nmsClass("PacketDataSerializer").getMethod("a", Enum.class).invoke(packetDataSerializerArg, enumHand);
                    packetConstructor = ServerReflection.nmsClass("PacketPlayOutCustomPayload").getConstructor(String.class, ServerReflection.nmsClass("PacketDataSerializer"));
                    openBook_Packet = packetConstructor.newInstance("MC|BOpen", packetDataSerializer);
                    break;

                case v1_8_R3:
                case v1_8_R2:
                case v1_8_R1:
                    packetDataSerializer = ServerReflection.nmsClass("PacketDataSerializer").getConstructor(ByteBuf.class).newInstance(Unpooled.buffer());
                    packetConstructor = ServerReflection.nmsClass("PacketPlayOutCustomPayload").getConstructor(String.class, ServerReflection.nmsClass("PacketDataSerializer"));
                    openBook_Packet = packetConstructor.newInstance("MC|BOpen", packetDataSerializer);
                    break;

                default:
                    openBook_Packet = null;
            }
        }
        catch (NoSuchFieldException | InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e)
        {
            e.printStackTrace();
            openBook_Packet = null;
        }
        OPENBOOK_PACKET = openBook_Packet;
    }

    public static void sendTitle(String title, String subtitle, int fadeIn, int stay, int fadeOut, Player... players)
    {
        if (NmsVersion.getCurrent().isAfterOrEq(NmsVersion.v1_11_R1))
        {
            for (Player player : players)
                player.sendTitle(title, subtitle, fadeIn, stay, fadeOut);
            return;
        }
        try
        {
            Object titleChat = ICHATBASECOMPONENT_A_METHOD.invoke(null, "{\"text\":\"" + title.replace("\\", "\\\\").replace("\\\"", "\"") + "\"}");
            Object subtitleChat = ICHATBASECOMPONENT_A_METHOD.invoke(null, "{\"text\":\"" + subtitle.replace("\\", "\\\\").replace("\\\"", "\"") + "\"}");

            Object titlePacket = TITLE_CONSTRUCTOR.newInstance(TITLE_ENUM, titleChat, fadeIn, stay, fadeOut);
            Object subtitlePacket = TITLE_CONSTRUCTOR.newInstance(SUBTITLE_ENUM, subtitleChat, fadeIn, stay, fadeOut);

            ServerReflection.sendPacket(titlePacket);
            ServerReflection.sendPacket(subtitlePacket);
        }
        catch (IllegalAccessException | InstantiationException | InvocationTargetException e)
        {
            e.printStackTrace();
        }
    }

    public static void sendActionBar(String text, Player... players)
    {
        try
        {
            if (text == null)
                text = "";

            Object chatText = ICHATBASECOMPONENT_A_METHOD.invoke(null, "{\"text\":\"" + text.replace("\\", "\\\\").replace("\"", "\\\"") + "\"}");
            Object titlePacket = ACTIONBAR_CONSTRUCTOR.newInstance(ACTIONBAR_ENUM, chatText);
            ServerReflection.sendPacket(titlePacket, players);
        }
        catch (IllegalAccessException | InvocationTargetException | InstantiationException e)
        {
            e.printStackTrace();
        }
    }

    public static void clearActionBar(Player... players)
    {
        sendActionBar("", players);
    }

    public static void clearTitle(Player... players)
    {
        sendTitle("", "", 0, 0, 0, players);
    }

    public static void sendTabTitle(String header, String footer, Player... players)
    {
        if (header == null)
            header = "";
        if (footer == null)
            footer = "";

        try
        {
            Object tabHeader = ICHATBASECOMPONENT_A_METHOD.invoke(null, "{\"text\":\"" + header + "\"}");
            Object tabFooter = ICHATBASECOMPONENT_A_METHOD.invoke(null, "{\"text\":\"" + footer + "\"}");
            Object packet = TABLIST_CONSTRUCTOR.newInstance(tabHeader);
            ServerReflection.setField(packet.getClass(), packet, "b", tabFooter);
            ServerReflection.sendPacket(packet, players);
        }
        catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | SecurityException e)
        {
            e.printStackTrace();
        }
    }

    public static int getPing(Player player)
    {
        int ping = -1;
        try
        {
            Object craftPlayer = ServerReflection.getHandle(player);
            if (craftPlayer != null)
                ping = (int) Objects.requireNonNull(ServerReflection.getDeclaredField(craftPlayer.getClass(), "ping")).get(craftPlayer);
        }
        catch (IllegalArgumentException | IllegalAccessException e)
        {
            e.printStackTrace();
        }
        return ping;
    }

    public static void openBook(ItemStack book, Player player)
    {
        if (ServerVersion.getCurrent().isAfterOrEq(ServerVersions.v1_14_2))
        {
            player.openBook(book);
            return;
        }
        int slot = player.getInventory().getHeldItemSlot();
        ItemStack old = player.getInventory().getItem(slot);
        player.getInventory().setItem(slot, book);
        ServerReflection.sendPacket(OPENBOOK_PACKET, player);
        player.getInventory().setItem(slot, old);
    }

    public static void forEachIfPlayer(Iterable<Object> objects, Consumer<Player> consumer)
    {
        for (Object o : objects)
            if (o instanceof Player)
                consumer.accept((Player) o);
    }

    public static Stream<Player> streamInRange(Location center, double radius)
    {
        return Objects.requireNonNull(center.getWorld()).getNearbyEntities(center, radius, radius, radius).stream()
                .filter(e -> e instanceof Player)
                .map(e -> ((Player) e));
    }

    public static void forEachInRange(Location center, double radius, Consumer<Player> consumer)
    {
        streamInRange(center, radius)
                .forEach(consumer);
    }

    public static void msg(CommandSender sender, String... messages)
    {
        for (String message : messages)
            sender.sendMessage(Text.setPlaceholders(sender, message));
    }

    public static void msg(String message, CommandSender... senders)
    {
        for (CommandSender sender : senders)
            sender.sendMessage(Text.setPlaceholders(sender, message));
    }

}
