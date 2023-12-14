package mod.linguardium.DeployerCrashFix.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.entity.ItemEntity;
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
    @ModifyExpressionValue(method="activateInner(Lcom/simibubi/create/content/kinetics/deployer/DeployerFakePlayer;Lnet/minecraft/class_243;Lnet/minecraft/class_2338;Lnet/minecraft/class_243;Lcom/simibubi/create/content/kinetics/deployer/DeployerBlockEntity$Mode;)V", at=@At(value="INVOKE",target="net/minecraft/class_1297.finishCapturingDrops()Ljava/util/List;"), remap = false)
    private static List<ItemEntity> nullCapturesAsEmpty(List<ItemEntity> original) {
        if (original == null) return new ArrayList<>();
        return original;
    }
}
