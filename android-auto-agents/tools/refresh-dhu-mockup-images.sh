#!/usr/bin/env bash
# Copy real Desktop Head Unit screenshot PNGs into the Svelte mockup.
# Usage:
#   ./tools/refresh-dhu-mockup-images.sh /path/to/browse.png /path/to/now-playing.png
# Or after saving in DHU terminal: screenshot /tmp/dhu-browse.png
set -euo pipefail
ROOT="$(cd "$(dirname "$0")/.." && pwd)"
DEST="$ROOT/mockup/public/dhu"
mkdir -p "$DEST"
if [[ $# -lt 2 ]]; then
  echo "Usage: $0 <browse.png> <now-playing.png>" >&2
  exit 1
fi
cp "$1" "$DEST/dhu-browse.png"
cp "$2" "$DEST/dhu-now-playing.png"
echo "Updated $DEST/dhu-browse.png and dhu-now-playing.png"
