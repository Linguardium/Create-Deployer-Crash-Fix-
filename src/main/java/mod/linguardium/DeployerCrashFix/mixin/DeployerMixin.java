package mod.linguardium.DeployerCrashFix.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.ModifyReceiver;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import mod.linguardium.DeployerCrashFix.DeployerHandlerLocal;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;

import java.util.ArrayList;
import java.util.List;

@Pseudo
@Mixin(targets = {"com.simibubi.create.content.kinetics.deployer.DeployerHandler"})
public class DeployerMixin {
    @Dynamic(value = "added by Porting-Lib Entity Extension in io.github.fabricators_of_create.porting_lib.entity.mixin.EntityMixin")
    @WrapOperation(method="activateInner(Lcom/simibubi/create/content/kinetics/deployer/DeployerFakePlayer;Lnet/minecraft/class_243;Lnet/minecraft/class_2338;Lnet/minecraft/class_243;Lcom/simibubi/create/content/kinetics/deployer/DeployerBlockEntity$Mode;)V", at=@At(value="INVOKE",target="net/minecraft/class_1297.finishCapturingDrops()Ljava/util/List;"), remap = false)
    private static List<ItemEntity> nullCapturesAsEmpty(Entity entity, Operation<List<ItemEntity>> oper) {
        if (entity instanceof LivingEntity) {
            ((DeployerHandlerLocal) entity).deployercrash$inDeployerHandler(false);
        }
        List<ItemEntity> list = oper.call(entity);
        if (list == null) return new ArrayList<>();
        return list;
    }
    @Dynamic(value = "Deployer from create")
    @ModifyReceiver(method="activateInner(Lcom/simibubi/create/content/kinetics/deployer/DeployerFakePlayer;Lnet/minecraft/class_243;Lnet/minecraft/class_2338;Lnet/minecraft/class_243;Lcom/simibubi/create/content/kinetics/deployer/DeployerBlockEntity$Mode;)V", at=@At(value="INVOKE",target="net/minecraft/class_1297.startCapturingDrops()V"), remap = false)
    private static Entity startCapturing(Entity original) {
        if (original instanceof LivingEntity) {
            ((DeployerHandlerLocal) original).deployercrash$inDeployerHandler(true);
        }
        return original;
    }
}
