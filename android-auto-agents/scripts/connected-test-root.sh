#!/usr/bin/env bash
# Deprecated alongside root `app/` — use plugin template + connected tests when needed.
# Example: PA2_PLUGIN_GRADLE_ROOT=.../PowerAmpache2PluginTemplate ./android-auto-agents/scripts/gradle-plugin-template.sh :app:connectedDebugAndroidTest
set -euo pipefail
echo "connected-test-root.sh: root app removed. Use gradle-plugin-template.sh with PA2_PLUGIN_GRADLE_ROOT." >&2
exit 1
