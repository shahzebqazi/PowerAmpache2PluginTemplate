#!/usr/bin/env bash
# Run Gradle in the PA2 plugin project (directory with ./gradlew).
# Requires PA2_PLUGIN_GRADLE_ROOT (see android-auto-agents/README.md).
# Usage: ./android-auto-agents/scripts/gradle-plugin-template.sh [gradle-args...]
#   ./android-auto-agents/scripts/gradle-plugin-template.sh :app:testDebugUnitTest
#   ./android-auto-agents/scripts/gradle-plugin-template.sh :app:assembleDebug
set -euo pipefail
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
# shellcheck source=lib/pa2-plugin-gradle-root.sh
source "$SCRIPT_DIR/lib/pa2-plugin-gradle-root.sh"
PLUGIN_ROOT="$(pa2_resolve_plugin_gradle_root)"
cd "$PLUGIN_ROOT"
exec ./gradlew "$@"
