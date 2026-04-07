import doc00 from '../../../docs/ux-research/00-executive-summary.md?raw'
import doc02 from '../../../docs/ux-research/02-task-analysis-and-flows.md?raw'
import doc07 from '../../../docs/ux-research/07-research-synthesis.md?raw'
import doc08 from '../../../docs/ux-research/08-mockup-handoff-package.md?raw'
import { renderMarkdown } from './renderMarkdown'

export type ResearchSection = {
  id: string
  title: string
  blurb: string
  sourcePath: string
  html: string
}

export const researchSections: ResearchSection[] = [
  {
    id: 'exec',
    title: 'Executive summary',
    blurb:
      'Goals, findings, and next steps: Media3 on the car, tasks that matter while driving, safety gates, and how phone theme differs from host UI.',
    sourcePath: 'docs/ux-research/00-executive-summary.md',
    html: renderMarkdown(doc00),
  },
  {
    id: 'synthesis',
    title: 'Research synthesis',
    blurb:
      'One narrative across platform reality, driver tasks, safety, accessibility, and open questions — with a table of implications per surface.',
    sourcePath: 'docs/ux-research/07-research-synthesis.md',
    html: renderMarkdown(doc07),
  },
  {
    id: 'tasks',
    title: 'Task analysis and flows',
    blurb:
      'T1–T5 task table, demand notes, Mermaid flows in the source (shown as code blocks here), and mapping to PA2 features.',
    sourcePath: 'docs/ux-research/02-task-analysis-and-flows.md',
    html: renderMarkdown(doc02),
  },
  {
    id: 'handoff',
    title: 'Mockups and implementation checklist',
    blurb:
      'P0–P3 scenarios, frame labels for phone vs host media, Media3 checklist, and open questions to resolve with the maintainer.',
    sourcePath: 'docs/ux-research/08-mockup-handoff-package.md',
    html: renderMarkdown(doc08),
  },
]
