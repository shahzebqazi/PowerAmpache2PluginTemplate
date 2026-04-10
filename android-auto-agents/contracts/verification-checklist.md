# Contract: verification checklist

Agents must not claim **complete** without satisfying the rows that apply.

## Build / test

- [ ] Correct **Gradle root** and module named in the handoff
- [ ] **`assembleDebug`** (or relevant variant) succeeds
- [ ] **`testDebugUnitTest`** (or scoped tasks) run; failures explained or fixed
- [ ] **Instrumented** tests run when the task touches Compose UI or integration

## Android Auto (when in scope)

- [ ] Correct **APK** installed (`luci.sixsixsix.powerampache2.plugin` or upstream id)
- [ ] **`./android-auto-agents/scripts/dhu-start.sh`** or **`auto`** used from **repo root** (not TCP)
- [ ] **Flows** listed (browse depth, play/pause, errors, etc.)
- [ ] Media/car changes cross-checked against **`android-auto-agents/contracts/android-auto-media-compliance-checklist.md`** and [car app quality](https://developer.android.com/docs/quality-guidelines/car-app-quality) (Media) where relevant

## Hygiene

- [ ] No secrets in diff (`local.properties`, tokens, passwords)
- [ ] **AGENTS.md** PRD row updated only when behaviour/backlog actually changed (coordinator)
