package me.toby.carbon.features.modules.render;

import net.minecraft.util.math.BlockPos;

import java.awt.Color;

import me.toby.carbon.event.events.Render3DEvent;
import me.toby.carbon.features.modules.Module;
import me.toby.carbon.features.setting.Setting;
import me.toby.carbon.util.RenderUtil;
import net.minecraft.util.math.RayTraceResult;

public class BlockHighlight extends Module
{
    private final Setting<Float> lineWidth;
    private final Setting<Integer> alpha;
    private final Setting<Integer> red;
    private final Setting<Integer> green;
    private final Setting<Integer> blue;
    
    public BlockHighlight() {
        super("BlockHighlight", "Highlights the block u look at.", Category.RENDER, false, false, false);
        this.lineWidth = (Setting<Float>)this.register(new Setting("LineWidth", 1.0f, 0.1f, 5.0f));
        this.alpha = (Setting<Integer>)this.register(new Setting("Alpha", 255, 0, 255));
        this.red = (Setting<Integer>)this.register(new Setting("Red", 255, 0, 255));
        this.green = (Setting<Integer>)this.register(new Setting("Green", 255, 0, 255));
        this.blue = (Setting<Integer>)this.register(new Setting("Blue", 255, 0, 255));
    }
    
    @Override
    public void onRender3D(final Render3DEvent event) {
        final RayTraceResult ray = BlockHighlight.mc.objectMouseOver;
        if (ray != null && ray.typeOfHit == RayTraceResult.Type.BLOCK) {
            final BlockPos blockpos = ray.getBlockPos();
            RenderUtil.drawBlockOutline(blockpos, new Color(this.red.getValue(), this.green.getValue(), this.blue.getValue(), this.alpha.getValue()), this.lineWidth.getValue(), false);
        }
    }
}
