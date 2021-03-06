package de.leonkoth.blockparty.util;

import de.leonkoth.blockparty.BlockParty;
import de.leonkoth.blockparty.version.VersionedMaterial;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.block.Block;

import static de.leonkoth.blockparty.locale.BlockPartyLocale.COLORS;

public class ColorBlock {

    private static String[] colors = COLORS.getValues();
    private static String[] colorCodes = {"f", "6", "d", "9", "e", "a", "c", "8", "7", "b", "5", "1", "8", "2", "4", "0"};

    @Getter
    private String name, colorCode;

    private ColorBlock(String name, String colorCode) {
        this.name = name;
        this.colorCode = colorCode;
    }

    public static ColorBlock get(Block block) {

        Material material = block.getType();
        String materialName = material.name();
        String name = format(material.name());
        String colorCode = "f";

        for (String suffix : VersionedMaterial.getColorableMaterialSuffix()) // TODO: Move or Replace
        {
            if (materialName.endsWith(suffix)) {
                byte data = BlockParty.getInstance().getBlockPlacer().getData(block.getWorld(), block.getX(), block.getY(), block.getZ());
                name = colors[data];
                colorCode = colorCodes[data];
                return new ColorBlock(name, colorCode);
            }
        }

        return new ColorBlock(name, colorCode);
    }

    private static String format(String materialName) {
        if (!materialName.contains("_")) {
            return capitalizeWord(materialName);
        }

        String[] words = materialName.split("_");
        String result = "";

        for (String word : words) {
            word = capitalizeWord(word);
            result += result.equalsIgnoreCase("") ? word : " " + word;
        }

        return result;
    }

    private static String capitalizeWord(String word) {
        String firstLetter = word.substring(0, 1).toUpperCase();
        String next = word.substring(1).toLowerCase();
        return firstLetter + next;
    }

}
