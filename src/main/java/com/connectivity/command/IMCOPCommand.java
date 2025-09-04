package com.connectivity.command;

import com.mojang.brigadier.context.CommandContext;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;

/**
 * Interface for commands requiring OP rights to execute.
 */
public interface IMCOPCommand extends IMCCommand
{
    /**
     * Executes pre-checks before issuing the command. Checks for the senders type and OP rights.
     */
    @Override
    default boolean checkPreCondition(final CommandContext<CommandSourceStack> context)
    {
        if (context.getSource().hasPermission(OP_PERM_LEVEL))
        {
            return true;
        }

        final Entity sender = context.getSource().getEntity();
        if (!(sender instanceof LocalPlayer player))
        {
            return false;
        }

        if (!IMCCommand.isPlayerOped(player))
        {
            player.displayClientMessage(Component.literal("You need to be OP for this command."), false);
            return false;
        }
        return true;
    }
}
