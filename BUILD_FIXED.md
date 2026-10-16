# SpecIal-Client build

Target: Minecraft 1.21.11, Java 21.

Fixes applied:
- Removed the obsolete `Session.AccountType.LEGACY` argument; the session now uses the five-argument constructor with `Optional.empty()` values.
- Pinned Fabric Loom to 1.14.10, matching the version reported in the build log.
- `RowButton.java` uses `Text.literal(...)` rather than the removed `LiteralText` class.

Build on a computer with internet access:
1. Install Java 21.
2. Install Gradle 9.x (the project has no Gradle Wrapper included).
3. Run `gradle build --no-daemon` from this folder.
4. The mod JAR should appear under `build/libs/`.

Note: this source package has not been verified by a successful Gradle build in the current environment because Gradle and Minecraft/Fabric dependencies could not be downloaded here. If the build reports further errors, share the complete output.
