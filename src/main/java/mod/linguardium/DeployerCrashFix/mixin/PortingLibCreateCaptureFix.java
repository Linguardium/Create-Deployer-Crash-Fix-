package mod.linguardium.DeployerCrashFix.mixin;

import com.bawnorton.mixinsquared.TargetHandler;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import mod.linguardium.DeployerCrashFix.DeployerHandlerLocal;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.function.Consumer;

@Mixin(value = LivingEntity.class, priority = 2000)
public abstract class PortingLibCreateCaptureFix extends EntityMixin {


    @TargetHandler(
            mixin = "io.github.fabricators_of_create.porting_lib.entity.mixin.LivingEntityMixin",
            name = "startCapturingDeathLoot"
    )
    @Inject(
            method = "@MixinSquared:Handler",
            at = @At("HEAD"),
            cancellable = true
    )
    private void dontStartCapturingForDeployer(DamageSource damageSource, CallbackInfo originalci, CallbackInfo info) {
        if (getCapturedDrops() != null) { info.cancel(); }
    }

    @TargetHandler(
            mixin = "io.github.fabricators_of_create.porting_lib.entity.mixin.LivingEntityMixin",
            name = "dropCapturedDrops"
    )
    @WrapOperation(
            method = "@MixinSquared:Handler",
            at = @At(value="INVOKE", target="java/util/List.forEach(Ljava/util/function/Consumer;)V")
    )
    private void reloadCapturedDropsForDeployer(List<ItemEntity> drops, Consumer<ItemEntity> dropperMethod, Operation<Void> forEachMethod) {
        if (((DeployerHandlerLocal)this).deployercrash$inDeployerHandler()) {
            if (getCapturedDrops() == null) {
                startCapturingDrops();
            }
            getCapturedDrops().addAll(drops);
        }else{
            forEachMethod.call(drops,dropperMethod);
        }
    }

}
