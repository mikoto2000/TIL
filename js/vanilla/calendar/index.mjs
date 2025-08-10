import { createCalendarView } from './calendar.mjs';

document.addEventListener('DOMContentLoaded', function() {

  // カレンダーの div を取得
  const calendarDiv = document.getElementById('calendar');
  calendarDiv.className = 'calendarContainer';

  // カレンダーの div が存在する場合のみ処理を続ける
  if (!calendarDiv) {
    console.error('Calendar div not found');
    return;
  }

  const currentDate = new Date();
  const calendar = createCalendarView(currentDate.getFullYear(), currentDate.getMonth());
  calendarDiv.appendChild(calendar);
});


