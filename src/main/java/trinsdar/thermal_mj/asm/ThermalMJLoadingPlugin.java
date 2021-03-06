package trinsdar.thermal_mj.asm;

import net.minecraft.launchwrapper.LaunchClassLoader;
import net.minecraftforge.common.ForgeVersion;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.relauncher.CoreModManager;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.Mixins;

import java.io.File;
import java.util.Map;

@IFMLLoadingPlugin.MCVersion(ForgeVersion.mcVersion)
@IFMLLoadingPlugin.TransformerExclusions("trinsdar.thermal_mj.asm")
@IFMLLoadingPlugin.SortingIndex(1001)
public class ThermalMJLoadingPlugin implements IFMLLoadingPlugin {
    public ThermalMJLoadingPlugin(){
        boolean thermal = false;
        boolean core = false;
        try {
            File mods = new File("./mods");
            for (File file : mods.listFiles()){
                if (file.getName().startsWith("ThermalExpansion") && !thermal){
                    loadModJar(file);
                    thermal = true;
                    continue;
                }
                if (file.getName().startsWith("CoFHCore") && !core){
                    loadModJar(file);
                    core = true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        MixinBootstrap.init();
        Mixins.addConfiguration("mixins.thermal_mj.core.json");
        if (thermal){
            Mixins.addConfiguration("mixins.thermal_mj.expansion.json");
        }
    }

    @Override
    public String[] getASMTransformerClass() {
        return new String[0];
    }

    @Override
    public String getModContainerClass() {
        return null;
    }

    @Nullable
    @Override
    public String getSetupClass() {
        return null;
    }

    @Override
    public void injectData(Map<String, Object> data) {

    }

    private void loadModJar(File jar) throws Exception {
        ((LaunchClassLoader) this.getClass().getClassLoader()).addURL(jar.toURI().toURL());
        CoreModManager.getReparseableCoremods().add(jar.getName());
    }

    @Override
    public String getAccessTransformerClass() {
        return null;
    }
}
