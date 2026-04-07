/** Mockup frame ids (sidebar + stage). Home is a separate route, not a frame. */
export type MockupFrameId =
  | 'auto-browse-playlists'
  | 'auto-browse-recommended'
  | 'auto-browse-recents'
  | 'auto-browse-frequent'
  | 'auto-browse-new'
  | 'auto-np'
  | 'auto-queue'
  | 'auto-error'

export const MOCKUP_FRAMES: readonly MockupFrameId[] = [
  'auto-browse-playlists',
  'auto-browse-recommended',
  'auto-browse-recents',
  'auto-browse-frequent',
  'auto-browse-new',
  'auto-np',
  'auto-queue',
  'auto-error',
] as const

export function isMockupFrameId(s: string): s is MockupFrameId {
  return (MOCKUP_FRAMES as readonly string[]).includes(s)
}
