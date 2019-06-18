import Vue from 'vue'
import Todo from '@/components/Todo'
import { mount } from '@vue/test-utils'


const nextTick = () => new Promise(res => process.nextTick(res));

describe('todo', () => {
  beforeEach(() => {
    fetch.resetMocks()
  })

  let todoComponent = (() => {
    fetch.mockResponseOnce([]);
    return mount(Todo)
  })

  it('exist todo input', () => {
    expect(todoComponent().find('input[name="todo_item"]').exists()).toBe(true)
  })
  it('exist todo register', () => {
    expect(todoComponent().find('#todo_register').exists()).toBe(true)
  })
  it('add todo', async () => {
    const wrapper = todoComponent()
    wrapper.find('[name="todo_item"]').setValue('555')
    wrapper.find('#todo_register').trigger('click')
    expect(wrapper.findAll('.todo_list input').length).toEqual(1)
    expect(wrapper.findAll('.todo_list input').at(0).element.value).toEqual("555")
  })
  it('regist todo', () => {
    const wrapper = todoComponent()
    wrapper.find('[name="todo_item"]').setValue('555')
    wrapper.find('#todo_register').trigger('click')
    expect(fetch.mock.calls.length).toEqual(2)
    expect(fetch.mock.calls[1][0]).toEqual('/todo')
    expect(JSON.parse(fetch.mock.calls[1][1]['body'])['inputValue']).toEqual(wrapper.vm.inputValue)
    expect(fetch.mock.calls[1][1]['method']).toEqual('POST')
  })
  it('get todo', async () => {
    const testValue = [{title:'ohuro souji'}];

    fetch.mockResponseOnce(JSON.stringify(testValue));
    const wrapper = mount(Todo)

    await nextTick()

    expect(fetch.mock.calls.length).toEqual(1)
    expect(fetch.mock.calls[0][0]).toEqual('/todo')
    expect(wrapper.vm.todos.length).toEqual(1)
    expect(wrapper.vm.todos).toEqual(testValue)
  })

})
