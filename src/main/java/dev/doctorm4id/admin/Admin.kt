package dev.doctorm4id.admin

import net.minecraft.resources.Identifier
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

object Admin {
    const val MOD_ID: String = /*$ mod_id*/"admin";
    const val MOD_VERSION: String =  /*$ mod_version*/"0.1.0";
    const val MOD_FRIENDLY_NAME: String =  /*$ mod_name*/"Admin";

    val Logger: Logger = LogManager.getLogger(MOD_FRIENDLY_NAME)

    fun idOf(unique: String): Identifier = Identifier.fromNamespaceAndPath(MOD_ID, unique)

    fun onInitialize() {
        Logger.info( "Initializing {}", MOD_ID)
        Logger.debug( "{}: { version: {}; friendly_name: {} }", MOD_ID, MOD_VERSION, MOD_FRIENDLY_NAME )
    }

    fun onInitializeClient() {
        Logger.info( "Initializing {} Client", MOD_ID)
        Logger.debug( "{}: { version: {}; friendly_name: {} }", MOD_ID, MOD_VERSION, MOD_FRIENDLY_NAME )
    }
}