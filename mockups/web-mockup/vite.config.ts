import path from 'node:path'
import { fileURLToPath } from 'node:url'
import { defineConfig } from 'vite'
import { svelte } from '@sveltejs/vite-plugin-svelte'

const __dirname = path.dirname(fileURLToPath(import.meta.url))
const repoRoot = path.resolve(__dirname, '..')

// GitHub Pages project site: set VITE_BASE_PATH=/pa2-car-plugin (no trailing slash) in CI.
function viteBase(): string {
  const raw = process.env.VITE_BASE_PATH?.trim()
  if (!raw) return '/'
  return raw.endsWith('/') ? raw : `${raw}/`
}

// https://vite.dev/config/
export default defineConfig({
  plugins: [svelte()],
  base: viteBase(),
  server: {
    fs: { allow: [repoRoot] },
  },
})
