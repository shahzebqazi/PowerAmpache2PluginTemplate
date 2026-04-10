#!/usr/bin/env bash
# shellcheck shell=bash
# Resolve workspace root when scripts live under android-auto-agents/scripts/.
# Usage: source this file from a script in android-auto-agents/scripts/ then:
#   aa_repo_root_from_script "${BASH_SOURCE[0]}"
aa_repo_root_from_script() {
  local here
  here="$(cd "$(dirname "$1")" && pwd)"
  echo "$(cd "$here/../.." && pwd)"
}
