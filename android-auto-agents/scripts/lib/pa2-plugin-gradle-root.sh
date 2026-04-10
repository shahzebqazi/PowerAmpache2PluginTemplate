#!/usr/bin/env bash
# Shared by harness scripts: resolve the PA2 Android Auto plugin Gradle root.
# Consumers set PA2_PLUGIN_GRADLE_ROOT to the directory that contains ./gradlew
# for that project (see android-auto-agents/README.md).
#
# If unset, tries sibling PowerAmpache2PluginTemplate/ next to android-auto-agents/
# (common layout in the android-auto consumer repo).
_PA2_PLUGIN_LIB_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
pa2_resolve_plugin_gradle_root() {
  local root="${PA2_PLUGIN_GRADLE_ROOT:-}"
  if [[ -z "$root" ]]; then
    local candidate="${_PA2_PLUGIN_LIB_DIR}/../../../PowerAmpache2PluginTemplate"
    if [[ -x "$candidate/gradlew" ]]; then
      root="$(cd "$candidate" && pwd)"
    fi
  fi
  if [[ -z "$root" ]]; then
    echo "PA2_PLUGIN_GRADLE_ROOT is not set. Export it to your PA2 plugin project's Gradle root (directory containing ./gradlew), or clone PowerAmpache2PluginTemplate next to android-auto-agents/." >&2
    return 1
  fi
  if [[ ! -x "$root/gradlew" ]]; then
    echo "PA2_PLUGIN_GRADLE_ROOT=$root does not contain an executable ./gradlew" >&2
    return 1
  fi
  printf '%s\n' "$root"
}
