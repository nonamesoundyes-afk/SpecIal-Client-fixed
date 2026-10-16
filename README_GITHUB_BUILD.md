# Build SpecIal on GitHub Actions

1. Create a new repository on GitHub. For a private project, choose **Private**.
2. Upload the contents of this folder to the repository root (so `build.gradle` and `.github/workflows/build.yml` are at the root).
3. Open the **Actions** tab and allow workflows if GitHub asks.
4. The workflow runs on push. Open the latest **Build SpecIal Fabric Mod** run.
5. If the build succeeds, download **SpecIal-build** from the Artifacts section at the bottom of the run summary.
6. Put the downloaded normal `.jar` in your Minecraft `mods` folder together with Fabric API. Do not install a `-sources.jar`.

If the build fails, open the run, select the failed job, and copy the first relevant `error:` / `FAILURE:` section from the logs for diagnosis.

Note: this workflow compiles the current source. It does not automatically implement features that are only listed as plans.
