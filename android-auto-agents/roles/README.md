# Agent roles

Each file is a **contract** for an autonomous or human-assisted agent. When orchestrating, one session should act as **coordinator** and route work by file.

| Role | File | Primary output |
|------|------|----------------|
| Coordinator | [`coordinator.md`](coordinator.md) | Backlog, boundaries, merge targets, “done” criteria |
| UX / UI researcher | [`ux-ui-researcher.md`](ux-ui-researcher.md) | Evidence-backed briefs, citations, guardrails |
| UI mockup developer | [`ui-mockup-developer.md`](ui-mockup-developer.md) | Svelte `mockup/` frames, handoff alignment |
| Developer | [`developer.md`](developer.md) | Kotlin/Gradle in the **correct module**, TDD |
| Tester | [`tester.md`](tester.md) | Test plans, Gradle commands, coverage of acceptance |
| Debugger | [`debugger.md`](debugger.md) | Repro, logcat, DHU/ADB, root-cause hypotheses |
| Shell / CI | [`shell-ci.md`](shell-ci.md) | Scripts, `adb`, DHU, CI interpretation |
