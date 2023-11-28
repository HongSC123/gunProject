 <template>
<div div class="p-grid p-fluid dashboard">
	<!-- 냉장고 -->
	<div class="p-col-12 p-sm-4 contacts">
		<Panel header="My 냉장고">
			<ul>
				<li>
					<button class="p-link">
						<img src="assets/layout/images/avatar_1.png" width="35" alt="avatar1"/>
						<span class="name">사과</span>
						<span class="email">0일 남음</span>
					</button>
				</li>
				<li>
					<button class="p-link">
						<img src="assets/layout/images/avatar_2.png" width="35" alt="avatar2"/>
						<span class="name">바나나</span>
						<span class="email">3일 남음</span>
					</button>
				</li>
				<li>
					<button class="p-link">
						<img src="assets/layout/images/avatar_3.png" width="35" alt="avatar3"/>
						<span class="name">포도</span>
						<span class="email">3일 남음</span>
					</button>
				</li>
				<li>
					<button class="p-link">
						<img src="assets/layout/images/avatar_4.png" width="35" alt="avatar4"/>
						<span class="name">돼지고기 등심</span>
						<span class="email">10일 남음</span>
					</button>
				</li>
			</ul>
		</Panel>
	</div>

	<!-- 체중차트 -->
	<div class="p-col-12 p-lg-8">
		<div class="card">
			<Chart type="line" :data="lineData" />
		</div>
	</div>

	<!-- 달력 -->
	<div class="p-col-12 p-lg-8">
		<Panel header="달력" style="height: 100%">
			<FullCalendar :events="events" :options="options" />
		</Panel>
	</div>

	<!-- 챗봇용 -->
	<div class="p-col-12 p-lg-4">
		<Panel header="공지사항" style="height: 100%">
			<ul class="activity-list">
				<li>
					가나다
				</li>
				<li>
					가나다
				</li>
				<li>
					가나다
				</li>
				<li>
					가나다
				</li>
				<li>
					가나다
				</li>
				<li>
					가나다
				</li>
				<li>
					가나다
				</li>
				<li>
					가나다
				</li>
				<li>
					가나다
				</li>
			</ul>
		</Panel>
	</div>
</div>
</template>

<script>
import dayGridPlugin from '@fullcalendar/daygrid';
import timeGridPlugin from '@fullcalendar/timegrid';
import interactionPlugin from '@fullcalendar/interaction';

export default {
	data() {
		return {
			//달력옵션
			options: {
				plugins:[dayGridPlugin, timeGridPlugin, interactionPlugin],
				defaultDate: '2019-01-01',
				header: {
					left: 'prev,next',
					center: 'title',
					right: 'dayGridMonth,timeGridWeek,timeGridDay'
				},
				editable: true
			},
			// 차트옵션
			lineData: {
				labels: ['7일전', '6일전', '5일전', '4일전', '3일전', '2일전', '1일전'],
				datasets: [
					{
						label: '체중',
						data: [65, 59, 80, 81, 56, 55, 40],
						fill: false,
						backgroundColor: '#2f4860',
						borderColor: '#2f4860'
					},
					{
						label: '배둘래',
						data: [28, 48, 40, 19, 86, 27, 90],
						fill: false,
						backgroundColor: '#00bb7e',
						borderColor: '#00bb7e'
					}
				]
			},
		}
	},
}
</script>

<style lang="scss" scoped>
	@media screen and (max-width: 960px) {
		::v-deep(.p-datatable) {
			&.p-datatable-customers {
				.p-datatable-thead > tr > th,
				.p-datatable-tfoot > tr > td {
					display: none !important;
				}

				.p-datatable-tbody > tr {
					border-bottom: 1px solid #dee2e6;
					> td {
						text-align: left;
						display: flex;
						align-items: center;
						justify-content: center;
						border: 0 none !important;
						width: 100% !important;
						float: left;
						clear: left;
						border: 0 none;

						.p-column-title {
							padding: .4rem;
							min-width: 30%;
							display: inline-block;
							margin: -.4rem 1rem -.4rem -.4rem;
							font-weight: bold;
						}

						.p-progressbar {
							margin-top: .5rem;
						}
					}
				}
			}
		}
	}
</style>
