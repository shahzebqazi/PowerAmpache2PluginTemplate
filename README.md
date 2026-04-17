# Plugin template for Power Ampache 2 (beta)

Android plugin shell for **Power Ampache 2**: `domain`, `data`, `app`, and `PowerAmpache2Theme` modules. This repository follows Clean Architecture; Android Auto / Media3 work lives primarily in **`app/`** (see [`AGENTS.md`](AGENTS.md)).

## Quick links

| Doc | Purpose |
|-----|---------|
| [`START_HERE.md`](START_HERE.md) | Onboarding, build notes, Android Auto / DHU, handoff for agents |
| [`AGENTS.md`](AGENTS.md) | Branch policy, commit format, Android Auto browse/search notes, iteration script |

## Branches (this fork)

- **`main`** — Tracks **`upstream/main`** only (`icefields/PowerAmpache2PluginTemplate`). No feature work here; sync with `git fetch upstream` and `git reset --hard upstream/main` when aligning with upstream.
- **`cursor-cloud/dev-main-4dc1`** — Integration branch for this fork; topic branches use the `cursor-cloud/` prefix.

## Contributing upstream (`PluginAndroidAuto`)

If you plan to contribute Android Auto–related changes **back to the upstream template**, read **Contributing upstream (PluginAndroidAuto branch)** in [`START_HERE.md`](START_HERE.md). It lists checks and open questions to settle **before** opening a pull request against upstream.
