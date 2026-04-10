/** Files in `public/` — prefix with Vite `base` so subpath deploys (e.g. GitHub Pages) work. */
export function publicUrl(pathFromPublicRoot: string): string {
  const trimmed = pathFromPublicRoot.replace(/^\/+/, '')
  return `${import.meta.env.BASE_URL}${trimmed}`
}
