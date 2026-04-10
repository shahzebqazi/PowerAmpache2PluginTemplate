#!/usr/bin/env bash
# Deprecated: this umbrella repo no longer includes a root Gradle project or `app/` module.
# Use the nested PA2 plugin template:
#   export PA2_PLUGIN_GRADLE_ROOT=/absolute/path/to/PowerAmpache2PluginTemplate
#   ./android-auto-agents/scripts/gradle-plugin-template.sh :app:testDebugUnitTest
set -euo pipefail
echo "gradle-root.sh: root Android module removed from this repo. Use gradle-plugin-template.sh with PA2_PLUGIN_GRADLE_ROOT pointing at PowerAmpache2PluginTemplate/ (see AGENTS.md)." >&2
exit 1
