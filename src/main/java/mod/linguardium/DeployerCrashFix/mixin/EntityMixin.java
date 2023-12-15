package mod.linguardium.DeployerCrashFix.mixin;

import mod.linguardium.DeployerCrashFix.DeployerHandlerLocal;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;

@Mixin(value = Entity.class)
public abstract class EntityMixin implements DeployerHandlerLocal {

    @Dynamic("Added by porting_lib entity module")
    @Shadow
    public abstract List<ItemEntity> getCapturedDrops();


    @Dynamic("Added by porting_lib entity module")
    @Shadow
    public abstract void startCapturingDrops();


    ThreadLocal<Boolean> insideDeployerHandler = ThreadLocal.withInitial(()->false);

    @Override
    public boolean deployercrash$inDeployerHandler() {
        return insideDeployerHandler.get();
    }

    @Override
    public void deployercrash$inDeployerHandler(boolean state) {
        insideDeployerHandler.set(state);
    }
}
