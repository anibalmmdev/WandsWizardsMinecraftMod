package com.anibalmm.wandswizards.data;

import net.minecraft.network.codec.ByteBufCodecs;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class ModAttachments {
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES =
            DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, "wandswizards");
 
   //Data saved to synchronize the visual of an entity being frozen
    public static final Supplier<AttachmentType<Boolean>> FROZEN_VISUAL =
            ATTACHMENT_TYPES.register("frozen_visual",
                    () -> AttachmentType.builder(() -> Boolean.FALSE)
                            .sync(ByteBufCodecs.BOOL)
                            .build());
}
