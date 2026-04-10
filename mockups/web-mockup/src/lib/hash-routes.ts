import { isMockupFrameId, type MockupFrameId } from './screen-ids'

export type AppRoute =
  | { name: 'home' }
  | { name: 'design' }
  | { name: 'research' }
  | { name: 'architecture' }
  | { name: 'mockup'; frame: MockupFrameId }

const DEFAULT_MOCKUP_FRAME: MockupFrameId = 'auto-browse-playlists'

/** Parse `location.hash` (e.g. `#/`, `#/design`, `#/mockup/auto-queue`). */
export function parseHash(hash: string): AppRoute {
  const raw = hash.replace(/^#/, '').replace(/^\//, '').trim()
  if (!raw) return { name: 'home' }

  const parts = raw.split('/').filter(Boolean)
  const head = parts[0]
  if (!head) return { name: 'home' }

  if (head === 'design' && parts.length === 1) return { name: 'design' }
  if (head === 'research' && parts.length === 1) return { name: 'research' }
  if (head === 'architecture' && parts.length === 1) return { name: 'architecture' }

  if (head === 'mockup') {
    const frame = parts[1]
    if (frame && isMockupFrameId(frame)) return { name: 'mockup', frame }
    return { name: 'mockup', frame: DEFAULT_MOCKUP_FRAME }
  }

  return { name: 'home' }
}

export function routeToHash(route: AppRoute): string {
  switch (route.name) {
    case 'home':
      return '#/'
    case 'design':
      return '#/design'
    case 'research':
      return '#/research'
    case 'architecture':
      return '#/architecture'
    case 'mockup':
      return `#/mockup/${route.frame}`
  }
}
