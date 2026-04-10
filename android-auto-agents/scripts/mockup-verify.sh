#!/usr/bin/env bash
# Design / stakeholder mockup: npm run build + npm run check under mockup/.
# Usage: ./android-auto-agents/scripts/mockup-verify.sh
# Env: SKIP_NPM_CI=1 to skip automatic npm ci when node_modules is missing
set -euo pipefail
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
REPO_ROOT="$(cd "$SCRIPT_DIR/../.." && pwd)"
MOCKUP="$REPO_ROOT/mockup"
if [[ ! -f "$MOCKUP/package.json" ]]; then
  echo "No mockup/package.json at $MOCKUP" >&2
  exit 1
fi
cd "$MOCKUP"
if [[ ! -d node_modules && "${SKIP_NPM_CI:-0}" != "1" ]]; then
  echo "Installing mockup dependencies (npm ci)…"
  npm ci
fi
npm run build
npm run check
