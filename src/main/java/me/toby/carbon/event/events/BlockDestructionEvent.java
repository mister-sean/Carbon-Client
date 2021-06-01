package me.toby.carbon.event.events;

import me.toby.carbon.event.EventStage;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public class BlockDestructionEvent extends EventStage {
    BlockPos nigger;
    public BlockDestructionEvent(BlockPos nigger){
        super();
        nigger = nigger;
    }

    public BlockPos getBlockPos(){
        return nigger;
    }
}
