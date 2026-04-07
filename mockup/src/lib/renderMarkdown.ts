import { marked } from 'marked'

marked.setOptions({ gfm: true })

/** Sync HTML from repo markdown (trusted content only). */
export function renderMarkdown(md: string): string {
  const out = marked.parse(md, { async: false })
  if (typeof out !== 'string') {
    throw new Error('renderMarkdown: expected synchronous parse')
  }
  return out
}
