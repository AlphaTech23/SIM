/* Import lang Files */
import french from '@/lang/french'

import { createI18n } from 'vue-i18n'
const i18n = createI18n({
  legacy: false,
  locale: 'fr',
  fallbackLocale: 'fr',
  messages: {
    fr: french.messages,
  },
  datetimeFormats: {
    fr: french.dateTimeFormats,
  },
  numberFormats: {
    fr: french.numberFormats,
  },
})
export default i18n
