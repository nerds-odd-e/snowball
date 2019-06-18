import Vue from 'vue'
import Todo from '@/components/Todo'

const nextTick = () => new Promise(res => process.nextTick(res));

describe('todo', () => {
  beforeEach(() => {
    fetch.resetMocks()
  })

  let todoComponent = (() => {
    fetch.mockResponseOnce([]);
    const Constructor = Vue.extend(Todo)
    return new Constructor().$mount()
  })

  it('exist todo input', () => {
    expect(todoComponent().$el.querySelector('input[name="todo_item"]').textContent).toEqual('')
  })

  it('exist todo register', () => {
    expect(todoComponent().$el.querySelector("#todo_register").textContent).toEqual('')
  })
  it('add todo', () => {
    const vm = todoComponent()
    vm.inputValue = 'ohuro souji'
    vm.addTodo()
    expect(vm.todos.length).toEqual(1)
    expect(vm.todos[0].title).toEqual(vm.inputValue)
  })
  it('regist todo', () => {
    const vm = todoComponent()
    vm.inputValue = 'ohuro souji'
    vm.addTodo()
    expect(fetch.mock.calls.length).toEqual(2)
    expect(fetch.mock.calls[1][0]).toEqual('/todo')
    expect(JSON.parse(fetch.mock.calls[1][1]['body'])['inputValue']).toEqual(vm.inputValue)
    expect(fetch.mock.calls[1][1]['method']).toEqual('POST')

  })

  it('get todo', async () => {
    const testValue = [{title:'ohuro souji'}];

    fetch.mockResponseOnce(JSON.stringify(testValue));
    const Constructor = Vue.extend(Todo)
    const vm = new Constructor().$mount()

    await nextTick()

    expect(fetch.mock.calls.length).toEqual(1)
    expect(fetch.mock.calls[0][0]).toEqual('/todo')
    expect(vm.todos.length).toEqual(1)
    expect(vm.todos).toEqual(testValue)



  })

//  it('fetch response and re render', async () => {
//    fetch.mockResponseOnce(JSON.stringify({msg:'hello!'}));
//    const Constructor = Vue.extend(Sample)
//    const vm = new Constructor().$mount()
//
//    await nextTick()
//
//    expect(fetch.mock.calls.length).toEqual(1)
//    expect(fetch.mock.calls[0][0]).toEqual('/sample')
//    expect(vm.message).toEqual('hello!')
//    expect(vm.$el.querySelector("#sample h2").textContent).toEqual('hello!')
//  })
})
