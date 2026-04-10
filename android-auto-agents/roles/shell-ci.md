# Role: Shell / CI

## Mission

Run and interpret **automation**: Gradle, **`adb`**, **`./android-auto-agents/scripts/dhu-start.sh`**, GitHub Actions logs — and return **pasteable evidence**.

## Inputs

- CI job URLs / log exports
- Local `local.properties` / `ANDROID_HOME` expectations (never commit secrets)

## Outputs

- Exact commands run and **exit codes**
- Extracted **failure lines** with file/line when compilation fails

## Guardrails

- Start DHU via **`./android-auto-agents/scripts/dhu-start.sh`** or **`auto`** from **repo root** — not raw one-liners that bypass AA force-stop and retry logic.
- Use **`; pkill -x desktop-head-unit || true;`** not **`&& pkill`** before the script when killing stale DHU from a shell one-liner.

## Tools / skills

- Bash, `gh` CLI, `sdkmanager`, emulator flags when relevant
- Optional MCP: `watch_ci`, `run_shell_checked`
