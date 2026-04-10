/**
 * Maps mockup frame ids to browse content for MediaLibraryCatalog-style lists.
 */
export type BrowseSectionKind = 'playlists' | 'recommended' | 'recents' | 'frequent' | 'new'

export type BrowseRow = { title: string; sub: string; accent?: boolean }

export type AutoBrowseFrameId =
  | 'auto-browse-playlists'
  | 'auto-browse-recommended'
  | 'auto-browse-recents'
  | 'auto-browse-frequent'
  | 'auto-browse-new'

export function isAutoBrowseFrameId(id: string): id is AutoBrowseFrameId {
  return (
    id === 'auto-browse-playlists' ||
    id === 'auto-browse-recommended' ||
    id === 'auto-browse-recents' ||
    id === 'auto-browse-frequent' ||
    id === 'auto-browse-new'
  )
}

export function browseSectionFromFrame(id: AutoBrowseFrameId): BrowseSectionKind {
  switch (id) {
    case 'auto-browse-playlists':
      return 'playlists'
    case 'auto-browse-recommended':
      return 'recommended'
    case 'auto-browse-recents':
      return 'recents'
    case 'auto-browse-frequent':
      return 'frequent'
    case 'auto-browse-new':
      return 'new'
  }
}

export function browseConfig(kind: BrowseSectionKind): {
  title: string
  frameLabel: string
  frameNote: string
  rows: BrowseRow[]
} {
  switch (kind) {
    case 'playlists':
      return {
        title: 'Playlists',
        frameLabel: 'Auto — Browse (playlists)',
        frameNote:
          'User and smart playlists; plugin exposes playable MediaItem folders. Host-rendered list; PA2 accents illustrate session identity.',
        rows: [
          { title: 'Road trip mix', sub: '24 tracks · Updated last week' },
          { title: 'Ampache favorites', sub: 'Smartlist · Demo server', accent: true },
          { title: 'Unplayed rock', sub: 'Smartlist · 18 tracks' },
          { title: 'Study focus', sub: '12 tracks' },
        ],
      }
    case 'recommended':
      return {
        title: 'Recommended',
        frameLabel: 'Auto — Browse (recommended)',
        frameNote:
          'Curated or server-driven suggestions as browsable/playable items. Same Media3 contract as other browse rows.',
        rows: [
          { title: 'Because you played M83', sub: 'Album · Hurry Up, We’re Dreaming', accent: true },
          { title: 'Fresh electronic', sub: 'Playlist · 20 tracks' },
          { title: 'Similar artists', sub: 'Radio seed · Demo' },
          { title: 'Weekend discovery', sub: 'Playlist · 15 tracks' },
        ],
      }
    case 'recents':
      return {
        title: 'Recents',
        frameLabel: 'Auto — Browse (recents)',
        frameNote:
          'Recently played albums, stations, or tracks — shallow navigation for quick resume in the car.',
        rows: [
          { title: 'Hurry Up, We’re Dreaming', sub: 'Album · M83 · Played 2h ago', accent: true },
          { title: 'Weekend mix', sub: 'Playlist · Played yesterday' },
          { title: 'Live session', sub: 'Radio · Played Mon' },
        ],
      }
    case 'frequent':
      return {
        title: 'Frequently played',
        frameLabel: 'Auto — Browse (frequently played)',
        frameNote:
          'High-play-count tracks or albums for one-tap recall. Populated from library history; host shows list.',
        rows: [
          { title: 'Midnight City', sub: 'M83 · 42 plays', accent: true },
          { title: 'Intro', sub: 'The xx · 38 plays' },
          { title: 'Blinding Lights', sub: 'The Weeknd · 35 plays' },
          { title: 'Reunion', sub: 'M83 · 31 plays' },
        ],
      }
    case 'new':
      return {
        title: 'Newly added',
        frameLabel: 'Auto — Browse (newly added)',
        frameNote:
          'Latest library additions (albums or tracks) sorted by added date for discovery.',
        rows: [
          { title: 'Glasshouse — EP', sub: 'Added 2 days ago · 6 tracks', accent: true },
          { title: 'North Coast Sessions', sub: 'Album · Added last week' },
          { title: 'Singles — March', sub: 'Playlist · Added 5 days ago' },
        ],
      }
  }
}
