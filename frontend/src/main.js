import {
  completeTask,
  createTask,
  deleteTask,
  getTask,
  listTasks,
  updateTask,
} from './api.js';
import './styles.css';

const state = {
  tasks: [],
  selectedTask: null,
  editingTask: null,
};

const elements = {
  form: document.querySelector('#task-form'),
  taskId: document.querySelector('#task-id'),
  title: document.querySelector('#title'),
  description: document.querySelector('#description'),
  submitButton: document.querySelector('#submit-button'),
  cancelEdit: document.querySelector('#cancel-edit'),
  formMessage: document.querySelector('#form-message'),
  taskList: document.querySelector('#task-list'),
  emptyState: document.querySelector('#empty-state'),
  taskCount: document.querySelector('#task-count'),
  detailTitle: document.querySelector('#detail-title'),
  detailDescription: document.querySelector('#detail-description'),
  detailStatus: document.querySelector('#detail-status'),
  detailCreated: document.querySelector('#detail-created'),
  completeButton: document.querySelector('#complete-button'),
  editButton: document.querySelector('#edit-button'),
  deleteButton: document.querySelector('#delete-button'),
};

elements.form.addEventListener('submit', handleSubmit);
elements.cancelEdit.addEventListener('click', clearEditing);
elements.completeButton.addEventListener('click', handleComplete);
elements.editButton.addEventListener('click', beginEditing);
elements.deleteButton.addEventListener('click', handleDelete);

loadTasks();

async function loadTasks() {
  try {
    state.tasks = await listTasks();
    renderTaskList();
    if (state.selectedTask) {
      const refreshed = state.tasks.find((task) => task.id === state.selectedTask.id);
      state.selectedTask = refreshed ? await getTask(refreshed.id) : null;
    }
    renderDetail();
  } catch (error) {
    setMessage(error.message, true);
  }
}

async function handleSubmit(event) {
  event.preventDefault();
  if (!elements.title.value.trim()) {
    setMessage('El titulo es obligatorio.', true);
    elements.title.focus();
    return;
  }

  const payload = {
    title: elements.title.value,
    description: elements.description.value || null,
  };

  try {
    if (state.editingTask) {
      state.selectedTask = await updateTask(state.editingTask.id, payload);
      setMessage('Tarea actualizada.');
    } else {
      state.selectedTask = await createTask(payload);
      setMessage('Tarea creada.');
    }
    clearEditing();
    await loadTasks();
  } catch (error) {
    setMessage(error.message, true);
  }
}

async function selectTask(id) {
  try {
    state.selectedTask = await getTask(id);
    renderDetail();
    renderTaskList();
  } catch (error) {
    setMessage(error.message, true);
  }
}

function beginEditing() {
  if (!state.selectedTask) {
    return;
  }
  state.editingTask = state.selectedTask;
  elements.taskId.value = state.selectedTask.id;
  elements.title.value = state.selectedTask.title;
  elements.description.value = state.selectedTask.description || '';
  elements.submitButton.textContent = 'Guardar cambios';
  elements.cancelEdit.hidden = false;
  elements.title.focus();
}

function clearEditing() {
  state.editingTask = null;
  elements.form.reset();
  elements.taskId.value = '';
  elements.submitButton.textContent = 'Crear tarea';
  elements.cancelEdit.hidden = true;
}

async function handleComplete() {
  if (!state.selectedTask) {
    return;
  }
  try {
    state.selectedTask = await completeTask(state.selectedTask.id);
    setMessage('Tarea completada.');
    await loadTasks();
  } catch (error) {
    setMessage(error.message, true);
  }
}

async function handleDelete() {
  if (!state.selectedTask) {
    return;
  }

  try {
    await deleteTask(state.selectedTask.id);
    state.selectedTask = null;
    clearEditing();
    setMessage('Tarea eliminada.');
    await loadTasks();
  } catch (error) {
    setMessage(error.message, true);
  }
}

function renderTaskList() {
  elements.taskList.innerHTML = '';
  elements.taskCount.textContent = String(state.tasks.length);
  elements.emptyState.hidden = state.tasks.length > 0;

  for (const task of state.tasks) {
    const item = document.createElement('li');
    const button = document.createElement('button');
    button.type = 'button';
    button.className = 'task-item';
    button.dataset.status = task.status;
    button.dataset.selected = String(state.selectedTask?.id === task.id);
    button.addEventListener('click', () => selectTask(task.id));

    const title = document.createElement('span');
    title.className = 'task-item__title';
    title.textContent = task.title;

    const meta = document.createElement('span');
    meta.className = 'task-item__meta';
    meta.textContent = `${formatStatus(task.status)} - ${formatDate(task.createdAt)}`;

    button.append(title, meta);
    item.append(button);
    elements.taskList.append(item);
  }
}

function renderDetail() {
  const task = state.selectedTask;
  const hasTask = Boolean(task);

  elements.detailTitle.textContent = task?.title || 'Selecciona una tarea';
  elements.detailDescription.textContent = task?.description || 'El detalle aparece aqui.';
  elements.detailStatus.textContent = task ? formatStatus(task.status) : '-';
  elements.detailCreated.textContent = task ? formatDate(task.createdAt) : '-';
  elements.completeButton.disabled = !hasTask || task.status === 'COMPLETED';
  elements.editButton.disabled = !hasTask;
  elements.deleteButton.disabled = !hasTask;
}

function setMessage(message, isError = false) {
  elements.formMessage.textContent = message;
  elements.formMessage.dataset.error = String(isError);
}

function formatStatus(status) {
  return status === 'COMPLETED' ? 'Completada' : 'Pendiente';
}

function formatDate(value) {
  return new Intl.DateTimeFormat('es', {
    dateStyle: 'medium',
    timeStyle: 'short',
  }).format(new Date(value));
}
