package org.example.sim.config;

import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

/**
 * Declare le schema d'authentification de Swagger (bouton "Authorize") et, par
 * @OpenAPIDefinition, l'applique a TOUTES les operations par defaut.
 *
 * Sans l'exigence globale, seules les methodes portant explicitement
 * @Operation(security = {...}) recevaient le jeton : 22 endpoints y echappaient
 * (les 20 /export/csv et les 2 /api/config/columns). Ils etaient bien refuses par
 * SecurityConfig, mais Swagger ne leur envoyait pas l'en-tete Authorization, donc
 * impossible de les tester meme apres avoir clique sur Authorize.
 *
 * L'exigence globale couvre aussi tout endpoint ajoute plus tard, sans annotation
 * a penser. Les rares routes publiques (/auth/login, /auth/refresh) la neutralisent
 * localement avec security = {}.
 */
@Configuration
@SecurityScheme(
    name = "bearerAuth",
    type = SecuritySchemeType.HTTP,
    scheme = "bearer",
    bearerFormat = "JWT"
)
public class SwaggerConfig {
}
